package com.example.quotivation.controller;

import com.example.quotivation.dto.author.response.AuthorInfo;
import com.example.quotivation.dto.category.response.CategoryInfo;
import com.example.quotivation.dto.quote.response.LatestQuote;
import com.example.quotivation.dto.quote.response.TodayQuote;
import com.example.quotivation.service.AuthorService;
import com.example.quotivation.service.CategoryService;
import com.example.quotivation.service.QuoteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final QuoteService quoteService;

    @GetMapping("/")
    public String home(Model model) {
        List<AuthorInfo> authors = authorService.getAuthors();
        List<CategoryInfo> categories = categoryService.getCategories();
        TodayQuote todayQuote = quoteService.getTodayQuote();
        List<LatestQuote> latestQuotes = quoteService.getLatestQuotes();



        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);
        model.addAttribute("todayQuote", todayQuote);
        model.addAttribute("latestQuotes", latestQuotes);

        return "index";
    }

    @GetMapping("/about/me")
    public String getAboutMePage(Model model, HttpServletRequest request) {
        Long quoteCount = quoteService.getAllCount();
        Long authorCount = authorService.getAllCount();

        model.addAttribute("quoteCount", quoteCount);
        model.addAttribute("authorCount", authorCount);
        model.addAttribute("url", request.getRequestURI());
        return "about/about-me-page";
    }

    @GetMapping("/about/contact")
    public String getAboutContactPage(Model model, HttpServletRequest request) {
        model.addAttribute("url", request.getRequestURI());
        return "about/about-contact-page";
    }
}
