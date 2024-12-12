package com.example.quotivation.service.api;

import com.example.quotivation.dto.quote.response.api.QuoteByCategoryResponse;
import com.example.quotivation.entity.Category;
import com.example.quotivation.entity.Quote;
import com.example.quotivation.repository.CategoryRepository;
import com.example.quotivation.repository.api.QuoteApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteApiService {

    private final QuoteApiRepository quoteApiRepository;
    private final CategoryRepository categoryRepository;

    public List<QuoteByCategoryResponse> getQuotesByCategory(String categoryName, Pageable pageable) {
        categoryName = categoryName.replace(" ", "");

        Optional<Category> category = categoryRepository.findByName(categoryName);
        if(category.isEmpty())
            throw new IllegalArgumentException("Category Not Found: " + categoryName);

        Page<Quote> categories = quoteApiRepository.findByCategory(category.get(), pageable);

        return categories.getContent().stream().map(QuoteByCategoryResponse::make).collect(Collectors.toList());
    }
}
