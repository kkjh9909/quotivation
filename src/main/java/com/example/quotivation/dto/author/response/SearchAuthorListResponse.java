package com.example.quotivation.dto.author.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SearchAuthorListResponse {

    private List<SearchAuthorResponse> authors;
    private Integer count;
    private Long totalCount;
}
