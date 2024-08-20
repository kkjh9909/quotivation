package com.example.quotivation.service;

import com.example.quotivation.dto.category.request.AddCategoryRequest;
import com.example.quotivation.dto.category.response.CategoryInfo;
import com.example.quotivation.dto.category.response.CategoryListInfo;
import com.example.quotivation.entity.Category;
import com.example.quotivation.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryInfo> getCategories() {
        List<Category> categories = categoryRepository.findAll(PageRequest.of(0, 10)).getContent();

        return categories.stream().map(CategoryInfo::make).collect(Collectors.toList());
    }

    public CategoryListInfo getAllCategories(Pageable pageable, String order) {
        Page<Category> categories;
        if(order.equals("popular"))
            categories = categoryRepository.findAllByOrderByQuoteCountDesc(pageable);
        else
            categories = categoryRepository.findAllByOrderByName(pageable);

        return new CategoryListInfo(categories.getContent().stream().map(CategoryInfo::make).collect(Collectors.toList()),
                categories.getTotalPages());
    }

    public String getCategoryName(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        return category.get().getName();
    }

    public List<CategoryInfo> getCategoriesForAddQuote() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(CategoryInfo::make).collect(Collectors.toList());
    }

    public void addCategory(AddCategoryRequest request) {
        Optional<Category> getCategory = categoryRepository.findByName(request.getCategory());
        if(getCategory.isPresent())
            throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");

        Category category = Category.make(request);

        categoryRepository.save(category);
    }
}
