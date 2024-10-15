package com.example.quotivation.dto.search.response;

import com.example.quotivation.dto.author.response.SearchAuthorListResponse;
import com.example.quotivation.dto.quote.response.SearchQuoteListResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SearchResultResponse {

    private SearchQuoteListResponse quotes;
    private SearchAuthorListResponse authors;

    public boolean hasQuotes() {
        return quotes != null;
    }

    public boolean hasAuthors() {
        return authors != null;
    }
}
