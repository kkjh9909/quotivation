package com.example.quotivation.dto.author.response;

import com.example.quotivation.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthorDetailsResponse {

    private Long id;
    private String name;
    private String picture;

    public static AuthorDetailsResponse make(Author author) {
        return AuthorDetailsResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .picture(author.getPhoto())
                .build();
    }
}
