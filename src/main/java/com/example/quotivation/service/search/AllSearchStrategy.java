package com.example.quotivation.service.search;

import com.example.quotivation.dto.author.response.SearchAuthorListResponse;
import com.example.quotivation.dto.quote.response.SearchQuoteListResponse;
import com.example.quotivation.dto.search.response.SearchResultResponse;
import com.example.quotivation.service.AuthorService;
import com.example.quotivation.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("all")
@RequiredArgsConstructor
public class AllSearchStrategy implements SearchStrategy {

    private final QuoteService quoteService;
    private final AuthorService authorService;

    @Override
    public SearchResultResponse search(String query, Pageable pageable) {
        SearchQuoteListResponse quotesResponse = quoteService.searchQuotesPreview(query);
        SearchAuthorListResponse authorsResponse = authorService.searchAuthorsPreview(query);

        return new SearchResultResponse(quotesResponse, authorsResponse);
    }
}
