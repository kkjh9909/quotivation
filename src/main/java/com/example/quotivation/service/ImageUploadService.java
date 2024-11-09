package com.example.quotivation.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageUploadService {

    private final AmazonS3Client amazonS3Client;
    private final AmazonS3Client r2Client;

    @Value("${cloud.aws.bucket}")
    private String s3Bucket;

    @Value("${cloud.r2.bucket}")
    private String r2Bucket;

    @Value("${cloud.r2.endpoint}")
    private String r2Endpoint;

    @Value("${cloud.r2.image-url}")
    private String r2ImageUrl;

    private String imageUrl;

    public void uploadImageToS3(MultipartFile file, String saveName) throws IOException {
        uploadImage(file, saveName, amazonS3Client, s3Bucket, "s3");
    }

    public void uploadImageToR2(MultipartFile file, String saveName) throws IOException {
        uploadImage(file, saveName, r2Client, r2Bucket, "r2");
    }

    public void uploadImage(MultipartFile file, String saveName, AmazonS3Client client, String bucket, String type) throws IOException {
        String filename = file.getOriginalFilename();

        if(file == null || !filename.contains("."))
            throw new IllegalArgumentException("유효하지 않은 파일입니다.");

        int lastDotIndex = filename.lastIndexOf(".");
        String extension = filename.substring(lastDotIndex + 1).toLowerCase();

        if (!verifyExtension(extension))
            throw new IllegalArgumentException("유효하지 않은 확장자명입니다.");

        String uploadedFile = "author/" + saveName + "." + extension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        File convertFile = convertFile(file);
        PutObjectRequest putRequest = new PutObjectRequest(bucket, uploadedFile, convertFile)
                .withMetadata(metadata);

        client.putObject(putRequest);

        if ("s3".equals(type))
            this.imageUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + uploadedFile;
        else if ("r2".equals(type))
            this.imageUrl = r2ImageUrl + "/" + uploadedFile;
    }



    public String getImageUrl() {
        return imageUrl;
    }

    private boolean verifyExtension(String ex) {
        String[] extensions = new String[]{"jpg", "jpeg", "png", "gif"};

        for (String extension : extensions) {
            if(ex.equals(extension))
                return true;
        }

        return false;
    }

    private File convertFile(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("temp", null);
        multipartFile.transferTo(file);
        return file;
    }
}

