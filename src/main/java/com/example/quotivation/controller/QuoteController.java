package com.example.quotivation.controller;

import com.example.quotivation.dto.author.response.AuthorInfo;
import com.example.quotivation.dto.author.response.AuthorListInfo;
import com.example.quotivation.dto.category.response.CategoryInfo;
import com.example.quotivation.service.AuthorService;
import com.example.quotivation.service.CategoryService;
import com.example.quotivation.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuoteController {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final QuoteService quoteService;

    @GetMapping("/add-quote")
    public String addQuotePage(Model model) {
        List<AuthorInfo> authors = authorService.getAuthorsForAddQuote();
        List<CategoryInfo> categories = categoryService.getCategoriesForAddQuote();

        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);

        return "add-quote-page";
    }


}
