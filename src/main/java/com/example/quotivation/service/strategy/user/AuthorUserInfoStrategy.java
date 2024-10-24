package com.example.quotivation.service.strategy.user;

import com.example.quotivation.dto.author.response.UserAuthorSubscriptionListResponse;
import com.example.quotivation.dto.author.response.UserAuthorSubscriptionResponse;
import com.example.quotivation.dto.common.SubscriptionCommonResponse;
import com.example.quotivation.entity.User;
import com.example.quotivation.entity.UserAuthorSubscription;
import com.example.quotivation.repository.UserAuthorSubscriptionRepository;
import com.example.quotivation.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("subscription_author")
@RequiredArgsConstructor
public class AuthorUserInfoStrategy implements UserInfoStrategy {

    private final AuthenticationService authenticationService;
    private final UserAuthorSubscriptionRepository userAuthorSubscriptionRepository;

    @Override
    public SubscriptionCommonResponse getSubscriptions(Pageable pageable) {
        User user = authenticationService.getUser();

        List<UserAuthorSubscription> subscriptions = userAuthorSubscriptionRepository.findByUser(user, pageable);
        Long count = userAuthorSubscriptionRepository.countByUser(user);

        return new UserAuthorSubscriptionListResponse(subscriptions.stream().map(UserAuthorSubscriptionResponse::make).toList(), count);
    }
}
