package com.example.quotivation.dto.quote.response;

import com.example.quotivation.entity.Quote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TodayQuote {

    private Long id;
    private String content;
    private String author;
    private String photo;

    public static TodayQuote make(Quote quote) {
        return TodayQuote.builder()
                .id(quote.getId())
                .content(quote.getContent())
                .author(quote.getAuthor().getName())
                .photo(quote.getAuthor().getPhoto())
                .build();
    }
}
