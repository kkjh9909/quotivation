package com.example.quotivation.controller;

import com.example.quotivation.dto.author.response.AuthorInfo;
import com.example.quotivation.repository.QuoteRepository;
import com.example.quotivation.service.AuthorService;
import com.example.quotivation.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final QuoteRepository quoteRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<AuthorInfo> authors = authorService.getAuthors();
        model.addAttribute("authors", authors);

        return "index";
    }
}
