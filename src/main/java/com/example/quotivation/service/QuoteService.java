package com.example.quotivation.service;

import com.example.quotivation.dto.quote.request.AddQuoteRequest;
import com.example.quotivation.dto.quote.response.*;
import com.example.quotivation.dto.quote.response.api.QuoteByCategoryResponse;
import com.example.quotivation.entity.*;
import com.example.quotivation.repository.*;
import com.example.quotivation.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final UserRepository userRepository;
    private final QuoteRepository quoteRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final QuoteLikeRepository quoteLikeRepository;
    private final AuthenticationService authenticationService;
    private final QuoteDislikeRepository quoteDislikeRepository;

    public TodayQuote getTodayQuote() {
        long count = quoteRepository.count();
        int randomNum = getRandomNumber(count);

        Quote quote = quoteRepository.findAll(PageRequest.of(randomNum, 1)).getContent().get(0);

        return TodayQuote.make(quote);
    }

    public List<LatestQuote> getLatestQuotes() {
        List<Quote> quotes = quoteRepository.findByOrderByUpdatedAtDesc(PageRequest.of(0, 10)).getContent();

        return quotes.stream().map(LatestQuote::make).collect(Collectors.toList());
    }

    public QuoteListInfo getQuotesByAuthor(Long authorId, Pageable pageable) {
        Optional<Author> author = authorRepository.findById(authorId);

        Page<Quote> quotes = quoteRepository.findByAuthor(author.get(), pageable);

        return new QuoteListInfo(quotes.stream().map(QuoteList::make).collect(Collectors.toList()),
                quotes.getTotalElements(), quotes.getTotalPages());
    }

    public QuoteListInfo getQuotesByCategory(Long categoryId, Pageable pageable) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        Page<Quote> quotes = quoteRepository.findByCategory(category.get(), pageable);

        return new QuoteListInfo(quotes.stream().map(QuoteList::make).collect(Collectors.toList()),
                quotes.getTotalElements(), quotes.getTotalPages());
    }

    @Transactional
    public void addQuote(AddQuoteRequest quoteRequest) {
        Optional<Category> category = categoryRepository.findById(quoteRequest.getCategoryId());
        Optional<Author> author = authorRepository.findById(quoteRequest.getAuthorId());

        Quote quote = Quote.create(category.get(), author.get(), quoteRequest.getContent());

        quoteRepository.save(quote);

        category.get().increaseQuoteCount();
        author.get().increaseQuoteCount();
    }

    public QuoteListInfo searchQuote(String query, Pageable pageable) {
        Page<Quote> quotes = quoteRepository.findByContentContaining(query, pageable);

        if(quotes.isEmpty())
            return new QuoteListInfo(new ArrayList<>(),0L, 0);
        else
            return new QuoteListInfo(quotes.getContent().stream().map(QuoteList::make).collect(Collectors.toList()),
                    quotes.getTotalElements(), quotes.getTotalPages());
    }

    public QuoteDetail getQuoteDetail(Long quoteId) {
        long userId = authenticationService.getUserId();

        Optional<Quote> quote = quoteRepository.findById(quoteId);
        Optional<User> user = userRepository.findById(userId);

        Optional<QuoteDislike> dislike = quoteDislikeRepository.findByQuoteIdAndUserId(quoteId, userId);
        Optional<QuoteLike> like = quoteLikeRepository.findByQuoteIdAndUserId(quoteId, userId);

        boolean isLike = like.isPresent();
        boolean isDislike = dislike.isPresent();

        return QuoteDetail.make(quote.get(), isLike, isDislike);
    }

    public List<RelatedQuote> getRelatedQuotes(Long quoteId) {
        Optional<Quote> quote = quoteRepository.findById(quoteId);

        Category category = quote.get().getCategory();

        return quoteRepository.findTop10ByCategoryOrderByUpdatedAtDesc(category, PageRequest.of(0, 10)).stream()
                .filter(q -> q.getId() != quoteId)
                .map(RelatedQuote::make)
                .collect(Collectors.toList());
    }

    public SearchQuoteListResponse searchQuotesPreview(String query) {
        Page<Quote> quotesPage = quoteRepository.searchByContent(query + "*", PageRequest.of(0, 5));
        List<Quote> quotes = quotesPage.getContent();
        Long totalCount = quotesPage.getTotalElements();

        return new SearchQuoteListResponse(quotes.stream().map(SearchQuoteResponse::make).collect(Collectors.toList()),
                quotes.size(), totalCount);
    }

    public SearchQuoteListResponse searchQuotesByPage(String query, Pageable pageable) {
        List<Quote> quotes = quoteRepository.findByContentContainingOrderByCreatedAtDesc(query, pageable);
        Long totalCount = quoteRepository.countByContentContaining(query);

        return new SearchQuoteListResponse(quotes.stream().map(SearchQuoteResponse::make).collect(Collectors.toList()),
                quotes.size(), totalCount);
    }

    public long getAllCount() {
        return quoteRepository.count();
    }

    private int getRandomNumber(long count) {
        Random random = new Random();

        if(count == 0)
            return -1;

        return random.nextInt((int)count);
    }
}
