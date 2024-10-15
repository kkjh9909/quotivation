package com.example.quotivation.dto.quote.response;

import com.example.quotivation.entity.Quote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SearchQuoteResponse {

    private Long id;
    private String content;
    private String author;
    private Long authorId;

    public static SearchQuoteResponse make(Quote quote) {
        return SearchQuoteResponse.builder()
                .id(quote.getId())
                .content(quote.getContent())
                .author(quote.getAuthor().getName())
                .authorId(quote.getAuthor().getId())
                .build();
    }
}
