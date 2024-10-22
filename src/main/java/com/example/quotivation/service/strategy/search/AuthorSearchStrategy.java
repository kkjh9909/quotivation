package com.example.quotivation.service.strategy.search;

import com.example.quotivation.dto.author.response.SearchAuthorListResponse;
import com.example.quotivation.dto.search.response.SearchResultResponse;
import com.example.quotivation.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("author")
@RequiredArgsConstructor
public class AuthorSearchStrategy implements SearchStrategy {

    private final AuthorService authorService;

    @Override
    public SearchResultResponse search(String query, Pageable pageable) {
        SearchAuthorListResponse authors = authorService.searchAuthorsByPage(query, pageable);

        return new SearchResultResponse(null, authors);
    }
}
