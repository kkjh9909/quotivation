package com.example.quotivation.dto.quote.response;

import com.example.quotivation.entity.Quote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LatestQuote {

    private Long id;
    private String content;

    public static LatestQuote make(Quote quote) {
        return LatestQuote.builder()
                .id(quote.getId())
                .content(quote.getContent())
                .build();
    }
}
