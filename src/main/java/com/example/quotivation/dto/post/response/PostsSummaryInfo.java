package com.example.quotivation.dto.post.response;

import com.example.quotivation.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
public class PostsSummaryInfo {

    private Long id;
    private String title;
    private String writer;
    private int hits;
    private LocalDateTime createdAt;

    public static PostsSummaryInfo make(Post post) {
        return PostsSummaryInfo.builder()
                .id(post.getId())
                .writer(post.getUser().getName())
                .title(post.getTitle())
                .hits(post.getHits())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
