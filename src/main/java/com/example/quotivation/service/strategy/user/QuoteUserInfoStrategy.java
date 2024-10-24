package com.example.quotivation.service.strategy.user;

import com.example.quotivation.dto.common.SubscriptionCommonResponse;
import com.example.quotivation.dto.quote.response.UserQuoteSubscriptionListResponse;
import com.example.quotivation.dto.quote.response.UserQuoteSubscriptionResponse;
import com.example.quotivation.entity.User;
import com.example.quotivation.entity.UserQuoteSubscription;
import com.example.quotivation.repository.UserQuoteSubscriptionRepository;
import com.example.quotivation.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("subscription_quote")
@RequiredArgsConstructor
public class QuoteUserInfoStrategy implements UserInfoStrategy {

    private final AuthenticationService authenticationService;
    private final UserQuoteSubscriptionRepository userQuoteSubscriptionRepository;

    @Override
    public SubscriptionCommonResponse getSubscriptions(Pageable pageable) {
        User user = authenticationService.getUser();

        List<UserQuoteSubscription> subscriptions = userQuoteSubscriptionRepository.findByUser(user, pageable);
        Long count = userQuoteSubscriptionRepository.countByUser(user);

        return new UserQuoteSubscriptionListResponse(subscriptions.stream().map(UserQuoteSubscriptionResponse::make).toList(), count);
    }
}
