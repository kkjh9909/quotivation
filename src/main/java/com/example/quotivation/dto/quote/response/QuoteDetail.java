package com.example.quotivation.dto.quote.response;

import com.example.quotivation.entity.Quote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
public class QuoteDetail {

    private Long id;
    private String content;
    private String photo;
    private String author;
    private int likeCount;
    private int dislikeCount;
    private Boolean isLike;
    private Boolean isDislike;
    private String updatedAt;

    public static QuoteDetail make(Quote quote, boolean isLike, boolean isDislike) {
        return QuoteDetail.builder()
                .id(quote.getId())
                .content(quote.getContent())
                .photo(quote.getAuthor().getPhoto())
                .author(quote.getAuthor().getName())
                .likeCount(quote.getLikeCount())
                .dislikeCount(quote.getDislikeCount())
                .isLike(isLike)
                .isDislike(isDislike)
                .updatedAt(quote.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
