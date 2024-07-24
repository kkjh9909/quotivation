package com.example.quotivation.service;

import com.example.quotivation.dto.category.response.CategoryInfo;
import com.example.quotivation.entity.Category;
import com.example.quotivation.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryInfo> getCategories() {
        List<Category> categories = categoryRepository.findAll(PageRequest.of(0, 10)).getContent();

        return categories.stream().map(CategoryInfo::make).collect(Collectors.toList());
    }
}
