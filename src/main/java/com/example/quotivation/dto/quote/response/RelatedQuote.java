package com.example.quotivation.dto.quote.response;

import com.example.quotivation.entity.Quote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RelatedQuote {

    private Long id;
    private String content;
    private String photo;
    private String author;

    public static RelatedQuote make(Quote quote) {
        return RelatedQuote.builder()
                .id(quote.getId())
                .content(quote.getContent())
                .photo(quote.getAuthor().getPhoto())
                .author(quote.getAuthor().getName())
                .build();
    }
}
