package com.example.quotivation.service;

import com.example.quotivation.dto.quote.request.AddQuoteRequest;
import com.example.quotivation.dto.quote.response.*;
import com.example.quotivation.entity.Author;
import com.example.quotivation.entity.Category;
import com.example.quotivation.entity.Quote;
import com.example.quotivation.repository.AuthorRepository;
import com.example.quotivation.repository.CategoryRepository;
import com.example.quotivation.repository.QuoteRepository;
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

    private final QuoteRepository quoteRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public TodayQuote getTodayQuote() {
        long count = quoteRepository.count();
        Random random = new Random();
        int i = random.nextInt((int)count);

        Quote quote = quoteRepository.findAll(PageRequest.of(i, 1)).getContent().get(0);

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
        Optional<Quote> quote = quoteRepository.findById(quoteId);
        
        return QuoteDetail.make(quote.get());
    }

    public List<RelatedQuote> getRelatedQuotes(Long quoteId) {
        Optional<Quote> quote = quoteRepository.findById(quoteId);

        Category category = quote.get().getCategory();

        return quoteRepository.findTop10ByCategoryOrderByUpdatedAtDesc(category).stream()
                .filter(q -> q.getId() != quoteId)
                .map(RelatedQuote::make)
                .collect(Collectors.toList());
    }
}
