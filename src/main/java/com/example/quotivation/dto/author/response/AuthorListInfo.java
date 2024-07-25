package com.example.quotivation.dto.author.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AuthorListInfo {

    private List<AuthorInfo> authors;
    private int totalPages;
}
