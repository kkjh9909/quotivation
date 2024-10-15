package com.example.quotivation.controller;

import com.example.quotivation.dto.search.response.SearchResultResponse;
import com.example.quotivation.service.search.SearchStrategy;
import com.example.quotivation.service.search.SearchStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final SearchStrategyFactory searchStrategyFactory;

    @GetMapping("/search")
    public String searchQuote(@RequestParam String query,
                              @RequestParam(defaultValue = "all") String type,
                              @PageableDefault(size = 5) Pageable pageable,
                              Model model) {

        SearchStrategy strategy = searchStrategyFactory.getStrategy(type);
        SearchResultResponse searchResult = strategy.search(query, pageable);

        if (searchResult.hasQuotes()) {
            model.addAttribute("quotes", searchResult.getQuotes());
            model.addAttribute("totalQuotesPages", (int)(searchResult.getQuotes().getTotalCount() - 1) / 5 + 1);
        }

        if (searchResult.hasAuthors()) {
            model.addAttribute("authors", searchResult.getAuthors());
            model.addAttribute("totalAuthorsPages", (int)(searchResult.getAuthors().getTotalCount() - 1) / 5 + 1);
        }

        model.addAttribute("query", query);
        model.addAttribute("type", type);
        model.addAttribute("currentPage", pageable.getPageNumber() + 1);

        return "quotes-search-page";
    }
}
