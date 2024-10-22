package com.example.quotivation.service.sort.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthorSortStrategyFactory {

    private final Map<String, AuthorSortStrategy> strategies;

    public AuthorSortStrategy getStrategy(String order) {
        return strategies.getOrDefault(order.toLowerCase(), strategies.get("name"));
    }
}
