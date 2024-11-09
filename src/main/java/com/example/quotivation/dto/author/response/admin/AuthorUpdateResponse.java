package com.example.quotivation.dto.author.response.admin;

import com.example.quotivation.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthorUpdateResponse {

    private Long id;
    private String name;

    public static AuthorUpdateResponse make(Author author) {
        return AuthorUpdateResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }
}
