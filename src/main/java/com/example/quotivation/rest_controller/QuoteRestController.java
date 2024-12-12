package com.example.quotivation.rest_controller;

import com.example.quotivation.dto.quote.response.api.QuoteByCategoryResponse;
import com.example.quotivation.service.QuoteService;
import com.example.quotivation.service.api.QuoteApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class QuoteRestController {

    private final QuoteService quoteService;
    private final QuoteApiService quoteApiService;

    @GetMapping("/quote")
    public ResponseEntity<?> getQuote(@RequestParam(name = "category", defaultValue = "동기 부여") String category) {
        QuoteByCategoryResponse response = quoteService.getQuoteByCategory(category);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/quotes")
    public ResponseEntity<?> getQuotes(@RequestParam(name = "category", defaultValue = "동기 부여") String category,
                                       @PageableDefault(size = 100) Pageable pageable) {
        List<QuoteByCategoryResponse> response = quoteApiService.getQuotesByCategory(category, pageable);

        return ResponseEntity.ok(response);
    }
}
