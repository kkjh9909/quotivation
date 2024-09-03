package com.example.quotivation.dto.author.response;

import com.example.quotivation.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthorDescriptionResponse {

    private String name;
    private String description;

    public static AuthorDescriptionResponse make(Author author) {
        return AuthorDescriptionResponse.builder()
                .name(author.getName())
                .description(author.getDescription())
                .build();
    }
}
