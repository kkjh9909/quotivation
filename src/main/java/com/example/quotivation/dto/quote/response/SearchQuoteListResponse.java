package com.example.quotivation.dto.quote.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SearchQuoteListResponse {

    private List<SearchQuoteResponse> quotes;
    private Integer count;
    private Long totalCount;
}
