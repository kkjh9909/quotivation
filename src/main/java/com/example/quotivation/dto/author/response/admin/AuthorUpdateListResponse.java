package com.example.quotivation.dto.author.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AuthorUpdateListResponse {

    private int count;
    private List<AuthorUpdateResponse> authors;
}
