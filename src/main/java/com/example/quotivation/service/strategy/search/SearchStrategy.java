package com.example.quotivation.service.strategy.search;

import com.example.quotivation.dto.search.response.SearchResultResponse;
import org.springframework.data.domain.Pageable;

public interface SearchStrategy {
    SearchResultResponse search(String query, Pageable pageable);
}
