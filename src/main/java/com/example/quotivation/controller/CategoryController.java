package com.example.quotivation.controller;

import com.example.quotivation.dto.category.response.CategoryListInfo;
import com.example.quotivation.service.CategoryService;
import com.example.quotivation.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final QuoteService quoteService;

    @GetMapping("/categories")
    public String getCategoriesPage(Model model,
                                    @RequestParam(value = "order", defaultValue = "alpha") String order,
                                    @PageableDefault(size = 10) Pageable pageable) {
        CategoryListInfo response = categoryService.getAllCategories(pageable, order);

        if(response.getTotalPages() <= pageable.getPageNumber())
            return String.format("redirect:/categories?page=%d&order=%s", response.getTotalPages(), order);

        model.addAttribute("totalPages", response.getTotalPages());
        model.addAttribute("categories", response.getCategories());
        model.addAttribute("currentPage", pageable.getPageNumber() + 1);
        model.addAttribute("sort", order);

        return "categories-page";
    }
}
