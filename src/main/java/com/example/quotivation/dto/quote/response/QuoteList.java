package com.example.quotivation.dto.quote.response;

import com.example.quotivation.entity.Quote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class QuoteList {

    private Long id;
    private String content;
    private String author;

    public static QuoteList make(Quote quote) {
        return QuoteList.builder()
                .id(quote.getId())
                .content(quote.getContent())
                .author(quote.getAuthor().getName())
                .build();
    }
}
