package com.example.quotivation.dto.quote.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddQuoteRequest {

    private Long categoryId;
    private Long authorId;
    private String content;
}
