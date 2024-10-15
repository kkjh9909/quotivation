package com.example.quotivation.service.search;

import com.example.quotivation.dto.search.response.SearchResultResponse;
import org.springframework.data.domain.Pageable;

public interface SearchStrategy {
    SearchResultResponse search(String query, Pageable pageable);
}
