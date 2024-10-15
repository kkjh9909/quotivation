package com.example.quotivation.dto.author.response;

import com.example.quotivation.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SearchAuthorResponse {

    private Long id;
    private String name;
    private String description;

    public static SearchAuthorResponse make(Author author) {
        return SearchAuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .description(author.getDescription())
                .build();
    }
}
