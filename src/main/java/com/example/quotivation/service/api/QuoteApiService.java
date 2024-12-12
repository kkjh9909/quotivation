package com.example.quotivation.service.api;

import com.example.quotivation.dto.quote.response.api.QuoteByCategoryResponse;
import com.example.quotivation.entity.Category;
import com.example.quotivation.entity.Quote;
import com.example.quotivation.repository.CategoryRepository;
import com.example.quotivation.repository.api.QuoteApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
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

    public QuoteByCategoryResponse getQuoteByCategory(String categoryName) {
        categoryName = categoryName.replace(" ", "");

        Optional<Category> category = categoryRepository.findByName(categoryName);
        if(category.isEmpty())
            throw new IllegalArgumentException("Category Not Found: " + categoryName);

        long count = quoteApiRepository.countByCategory(category.get());

        int randomNum = getRandomNumber(count);
        Quote quote = quoteApiRepository.findByCategory(category.get(), PageRequest.of(randomNum, 1)).getContent().get(0);

        return QuoteByCategoryResponse.make(quote);
    }

    private int getRandomNumber(long count) {
        Random random = new Random();

        if(count == 0)
            return -1;

        return random.nextInt((int)count);
    }
}
