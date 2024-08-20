package com.example.quotivation.dto.category.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddCategoryRequest {

    private String category;

    public AddCategoryRequest() {}
}
