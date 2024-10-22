package com.example.quotivation.service.sort.category;

import com.example.quotivation.dto.category.response.CategoryListInfo;
import org.springframework.data.domain.Pageable;

public interface CategorySortStrategy {

    CategoryListInfo sort(Pageable pageable);
}
