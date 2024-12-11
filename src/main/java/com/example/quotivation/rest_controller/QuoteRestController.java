package com.example.quotivation.rest_controller;

import com.example.quotivation.dto.quote.response.api.QuoteByCategoryResponse;
import com.example.quotivation.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class QuoteRestController {

    private final QuoteService quoteService;

    @GetMapping("/quote")
    public ResponseEntity<?> getQuotes(@RequestParam(name = "category", defaultValue = "동기 부여") String category) {
        QuoteByCategoryResponse response = quoteService.getQuoteByCategory(category);

        return ResponseEntity.ok(response);
    }
}
