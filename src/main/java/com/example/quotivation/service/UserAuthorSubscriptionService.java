package com.example.quotivation.service;

import com.example.quotivation.entity.Author;
import com.example.quotivation.entity.QuoteLike;
import com.example.quotivation.entity.User;
import com.example.quotivation.entity.UserAuthorSubscription;
import com.example.quotivation.repository.AuthorRepository;
import com.example.quotivation.repository.UserAuthorSubscriptionRepository;
import com.example.quotivation.repository.UserRepository;
import com.example.quotivation.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthorSubscriptionService {

    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final AuthenticationService authenticationService;
    private final UserAuthorSubscriptionRepository userAuthorSubscriptionRepository;

    public Boolean isSubscribe(Long authorId) {
        User user = authenticationService.getUser();

        Author author = authorRepository.findById(authorId).get();

        Optional<UserAuthorSubscription> isSubscribe = userAuthorSubscriptionRepository.findByUserIdAndAuthorId(user.getId(), author.getId());

        return isSubscribe.isPresent();
    }

    public void subscribe(Long authorId) {
        User user = authenticationService.getUser();

        Author author = authorRepository.findById(authorId).get();

        Optional<UserAuthorSubscription> isSubscribe = userAuthorSubscriptionRepository.findByUserIdAndAuthorId(user.getId(), author.getId());
        if(isSubscribe.isEmpty()) {
            UserAuthorSubscription subscription = UserAuthorSubscription.make(user, author);
            subscription.subscribe();
            userAuthorSubscriptionRepository.save(subscription);
        }
    }

    public void unsubscribe(Long authorId) {
        User user = authenticationService.getUser();

        Author author = authorRepository.findById(authorId).get();

        Optional<UserAuthorSubscription> isSubscribe = userAuthorSubscriptionRepository.findByUserIdAndAuthorId(user.getId(), author.getId());
        if(isSubscribe.isPresent()) {
            isSubscribe.get().unsubscribe();
            userAuthorSubscriptionRepository.delete(isSubscribe.get());
        }
    }
}
