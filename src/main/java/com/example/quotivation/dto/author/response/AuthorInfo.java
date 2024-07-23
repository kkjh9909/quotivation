package com.example.quotivation.dto.author.response;

import com.example.quotivation.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthorInfo {

    private Long id;
    private String name;
    private int count;

    public static AuthorInfo make(Author author) {
        return AuthorInfo.builder()
                .id(author.getId())
                .name(author.getName())
                .count(author.getQuoteCount())
                .build();
    }
}
