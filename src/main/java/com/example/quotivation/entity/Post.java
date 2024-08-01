package com.example.quotivation.entity;

import com.example.quotivation.dto.post.request.PostWriteReq;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Post extends Timestamp {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String content;

    private String writer;

    private String address;

    private int hits;

    public Post() {}

    public static Post create(PostWriteReq request, String ip) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(request.getWriter())
                .address(ip)
                .hits(0)
                .build();
    }
}
