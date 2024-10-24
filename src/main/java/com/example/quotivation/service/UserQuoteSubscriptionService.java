package com.example.quotivation.service;

import com.example.quotivation.entity.*;
import com.example.quotivation.repository.QuoteRepository;
import com.example.quotivation.repository.UserQuoteSubscriptionRepository;
import com.example.quotivation.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserQuoteSubscriptionService {

    private final QuoteRepository quoteRepository;
    private final AuthenticationService authenticationService;
    private final UserQuoteSubscriptionRepository userQuoteSubscriptionRepository;

    public void subscribe(Long quoteId) {
        User user = authenticationService.getUser();

        Quote quote = quoteRepository.findById(quoteId).get();

        Optional<UserQuoteSubscription> isSubscribe = userQuoteSubscriptionRepository.findByUserIdAndQuoteId(user.getId(), quote.getId());
        if(isSubscribe.isEmpty()) {
            UserQuoteSubscription subscription = UserQuoteSubscription.make(user, quote);
            subscription.subscribe();
            userQuoteSubscriptionRepository.save(subscription);
        }
    }

    public void unsubscribe(Long quoteId) {
        User user = authenticationService.getUser();

        Quote quote = quoteRepository.findById(quoteId).get();

        Optional<UserQuoteSubscription> isSubscribe = userQuoteSubscriptionRepository.findByUserIdAndQuoteId(user.getId(), quote.getId());
        if(isSubscribe.isPresent()) {
            isSubscribe.get().unsubscribe();
            userQuoteSubscriptionRepository.delete(isSubscribe.get());
        }
    }

    public Boolean isSubscribe(Long quoteId) {
        User user = authenticationService.getUser();
        Quote quote = quoteRepository.findById(quoteId).get();

        Optional<UserQuoteSubscription> isSubscribe = userQuoteSubscriptionRepository.findByUserIdAndQuoteId(user.getId(), quote.getId());

        return isSubscribe.isPresent();
    }
}
