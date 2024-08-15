package com.example.quotivation.service;

import com.example.quotivation.entity.Quote;
import com.example.quotivation.entity.QuoteDislike;
import com.example.quotivation.entity.QuoteLike;
import com.example.quotivation.entity.User;
import com.example.quotivation.repository.QuoteDislikeRepository;
import com.example.quotivation.repository.QuoteLikeRepository;
import com.example.quotivation.repository.QuoteRepository;
import com.example.quotivation.repository.UserRepository;
import com.example.quotivation.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuoteDislikeService {

    private final UserRepository userRepository;
    private final QuoteRepository quoteRepository;
    private final AuthenticationService authenticationService;
    private final QuoteDislikeRepository quoteDislikeRepository;

    @Transactional
    public int increaseDislike(Long quoteId) {
        long userId = authenticationService.getUserId();

        Optional<Quote> quote = quoteRepository.findById(quoteId);
        Optional<User> user = userRepository.findById(userId);

        Optional<QuoteDislike> isLike = quoteDislikeRepository.findByQuoteIdAndUserId(quoteId, userId);

        if(isLike.isEmpty()) {
            QuoteDislike like = QuoteDislike.make(user.get(), quote.get());
            like.addDislike();
            quoteDislikeRepository.save(like);
        }

        return quote.get().getDislikeCount();
    }

    @Transactional
    public int decreaseDislike(Long quoteId) {
        long userId = authenticationService.getUserId();

        Optional<Quote> quote = quoteRepository.findById(quoteId);

        Optional<QuoteDislike> isLike = quoteDislikeRepository.findByQuoteIdAndUserId(quoteId, userId);

        if(isLike.isPresent()) {
            isLike.get().deleteDislike();
            quoteDislikeRepository.delete(isLike.get());
        }

        return quote.get().getDislikeCount();
    }

    public Boolean isLike(Long quoteId) {
        long userId = authenticationService.getUserId();

        Optional<QuoteDislike> like = quoteDislikeRepository.findByQuoteIdAndUserId(quoteId, userId);

        return like.isPresent();
    }
}
