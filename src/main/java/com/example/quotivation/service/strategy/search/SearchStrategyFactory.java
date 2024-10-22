package com.example.quotivation.service.strategy.search;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchStrategyFactory {

    private final Map<String, SearchStrategy> strategies;

    public SearchStrategy getStrategy(String type) {
        return strategies.getOrDefault(type.toLowerCase(), strategies.get("all"));
    }
}
