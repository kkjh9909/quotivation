package com.example.quotivation.service;

import com.example.quotivation.entity.Quote;
import com.example.quotivation.entity.QuoteLike;
import com.example.quotivation.entity.User;
import com.example.quotivation.repository.QuoteRepository;
import com.example.quotivation.repository.UserRepository;
import com.example.quotivation.security.AuthenticationService;
import com.example.quotivation.security.JwtProvider;
import com.example.quotivation.repository.QuoteLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuoteLikeService {

    private final UserRepository userRepository;
    private final QuoteRepository quoteRepository;
    private final QuoteLikeRepository quoteLikeRepository;
    private final AuthenticationService authenticationService;

    @Transactional
    public int increaseLike(Long quoteId) {
        long userId = authenticationService.getUserId();

        Optional<Quote> quote = quoteRepository.findById(quoteId);
        Optional<User> user = userRepository.findById(userId);

        Optional<QuoteLike> isLike = quoteLikeRepository.findByQuoteIdAndUserId(quoteId, userId);

        if(isLike.isEmpty()) {
            QuoteLike like = QuoteLike.make(user.get(), quote.get());
            like.addLike();
            quoteLikeRepository.save(like);
        }

        return quote.get().getLikeCount();
    }

    @Transactional
    public int decreaseLike(Long quoteId) {
        long userId = authenticationService.getUserId();

        Optional<Quote> quote = quoteRepository.findById(quoteId);

        Optional<QuoteLike> isLike = quoteLikeRepository.findByQuoteIdAndUserId(quoteId, userId);

        if(isLike.isPresent()) {
            isLike.get().deleteLike();
            quoteLikeRepository.delete(isLike.get());
        }

        return quote.get().getLikeCount();
    }

    public Boolean isLike(Long quoteId) {
        long userId = authenticationService.getUserId();
        Optional<QuoteLike> like = quoteLikeRepository.findByQuoteIdAndUserId(quoteId, userId);

        return like.isPresent();
    }
}
