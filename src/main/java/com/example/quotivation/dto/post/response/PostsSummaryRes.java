package com.example.quotivation.dto.post.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PostsSummaryRes {

    private int totalPages;
    private List<PostsSummaryInfo> posts;
}
