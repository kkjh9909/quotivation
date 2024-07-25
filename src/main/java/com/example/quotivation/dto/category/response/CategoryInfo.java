package com.example.quotivation.dto.category.response;

import com.example.quotivation.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryInfo {

    private Long id;
    private String name;
    private int count;

    public static CategoryInfo make(Category category) {
        return CategoryInfo.builder()
                .id(category.getId())
                .name(category.getName())
                .count(category.getQuoteCount())
                .build();
    }
}
