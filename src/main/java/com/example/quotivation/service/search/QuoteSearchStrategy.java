package com.example.quotivation.service.search;

import com.example.quotivation.dto.quote.response.SearchQuoteListResponse;
import com.example.quotivation.dto.search.response.SearchResultResponse;
import com.example.quotivation.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("sentence")
@RequiredArgsConstructor
public class QuoteSearchStrategy implements SearchStrategy {

    private final QuoteService quoteService;

    @Override
    public SearchResultResponse search(String query, Pageable pageable) {
        SearchQuoteListResponse quotes = quoteService.searchQuotesByPage(query, pageable);

        return new SearchResultResponse(quotes, null);
    }
}
