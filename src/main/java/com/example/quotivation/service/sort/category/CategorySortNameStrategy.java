package com.example.quotivation.service.sort.category;

import com.example.quotivation.dto.category.response.CategoryInfo;
import com.example.quotivation.dto.category.response.CategoryListInfo;
import com.example.quotivation.entity.Category;
import com.example.quotivation.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service("category_name")
@RequiredArgsConstructor
public class CategorySortNameStrategy implements CategorySortStrategy {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryListInfo sort(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAllByOrderByName(pageable);

        return new CategoryListInfo(categories.getContent().stream().map(CategoryInfo::make).collect(Collectors.toList()),
                categories.getTotalPages());
    }
}
