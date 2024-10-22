package com.example.quotivation.service.sort.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategorySortStrategyFactory {

    private final Map<String, CategorySortStrategy> strategies;

    public CategorySortStrategy getStrategy(String order) {
        return strategies.getOrDefault("category_" + order.toLowerCase(), strategies.get("category_name"));
    }
}
