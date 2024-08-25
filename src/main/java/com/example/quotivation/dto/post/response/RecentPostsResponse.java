package com.example.quotivation.dto.post.response;

import com.example.quotivation.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class RecentPostsResponse {

    private Long id;
    private String title;
    private String writer;
    private int hits;
    private LocalDateTime createdAt;

    public static RecentPostsResponse make(Post post) {
        return RecentPostsResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .writer(post.getUser().getName())
                .hits(post.getHits())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
