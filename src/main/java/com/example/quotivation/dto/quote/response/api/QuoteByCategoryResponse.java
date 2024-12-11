package com.example.quotivation.dto.quote.response.api;

import com.example.quotivation.entity.Quote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class QuoteByCategoryResponse {

    private String content;
    private String author;
    private String category;

    public static QuoteByCategoryResponse make(Quote quote) {
        return QuoteByCategoryResponse.builder()
                .content(quote.getContent())
                .author(quote.getAuthor().getName())
                .category(quote.getCategory().getName())
                .build();
    }
}
