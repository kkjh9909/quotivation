package com.example.quotivation.dto.category.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CategoryListInfo {

    private List<CategoryInfo> categories;
    private int totalPages;
}
