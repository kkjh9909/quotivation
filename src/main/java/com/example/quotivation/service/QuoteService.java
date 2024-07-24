package com.example.quotivation.service;

import com.example.quotivation.dto.quote.response.LatestQuote;
import com.example.quotivation.dto.quote.response.TodayQuote;
import com.example.quotivation.entity.Quote;
import com.example.quotivation.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;

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
}
