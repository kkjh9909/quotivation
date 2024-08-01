package com.example.quotivation.dto.post.response;

import com.example.quotivation.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class RecentPostsRes {

    private Long id;
    private String title;
    private String writer;
    private String address;
    private int hits;
    private LocalDateTime createdAt;

    public static RecentPostsRes make(Post post) {
        return RecentPostsRes.builder()
                .id(post.getId())
                .title(post.getTitle())
                .writer(post.getWriter())
                .address(post.getAddress())
                .hits(post.getHits())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
