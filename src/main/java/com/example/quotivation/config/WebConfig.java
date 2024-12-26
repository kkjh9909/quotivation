package com.example.quotivation.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class WebConfig {

    @Value("${cloud.aws.credentials.access-key}")
    private String s3AccessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String s3SecretKey;

    @Value("${cloud.aws.region}")
    private String s3Region;

    @Value("${cloud.r2.credentials.access-key}")
    private String r2AccessKey;

    @Value("${cloud.r2.credentials.secret-key}")
    private String r2SecretKey;

    @Value("${cloud.r2.region}")
    private String r2Region;

    @Value("${cloud.r2.endpoint}")
    private String r2Endpoint;

    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(s3AccessKey, s3SecretKey);

        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(s3Region)
                .build();
    }

    @Bean
    public AmazonS3Client r2Client() {
        BasicAWSCredentials r2Credentials = new BasicAWSCredentials(r2AccessKey, r2SecretKey);

        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(r2Credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(r2Endpoint, r2Region))
                .enablePathStyleAccess()
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
