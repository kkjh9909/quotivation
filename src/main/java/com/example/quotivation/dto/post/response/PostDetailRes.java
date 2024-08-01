package com.example.quotivation.dto.post.response;

import com.example.quotivation.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class PostDetailRes {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String address;
    private int hits;
    private LocalDateTime createdAt;

    public static PostDetailRes make(Post post) {
        return PostDetailRes.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getWriter())
                .address(post.getAddress())
                .hits(post.getHits())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
