package com.example.quotivation.controller;

import com.example.quotivation.dto.author.response.AuthorListInfo;
import com.example.quotivation.service.AuthorService;
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
public class AuthorController {

    private final AuthorService authorService;
    private final QuoteService quoteService;

    @GetMapping("/authors")
    public String getAuthorPage(Model model,
                                @RequestParam(value = "order", defaultValue = "alpha") String order,
                                @PageableDefault(size = 10) Pageable pageable) {
        AuthorListInfo response = authorService.getAllAuthors(pageable, order);

        if(response.getTotalPages() <= pageable.getPageNumber())
            return String.format("redirect:/authors?page=%d&order=%s", response.getTotalPages(), order);

        model.addAttribute("totalPages", response.getTotalPages());
        model.addAttribute("authors", response.getAuthors());
        model.addAttribute("currentPage", pageable.getPageNumber() + 1);
        model.addAttribute("sort", order);

        return "authors-page";
    }
}
