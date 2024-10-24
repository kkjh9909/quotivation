package com.example.quotivation.repository;

import com.example.quotivation.entity.Author;
import com.example.quotivation.entity.User;
import com.example.quotivation.entity.UserAuthorSubscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAuthorSubscriptionRepository extends JpaRepository<UserAuthorSubscription, Long> {
    Optional<UserAuthorSubscription> findByUserAndAuthor(User user, Author author);

    List<UserAuthorSubscription> findByUser(User user, Pageable pageable);

    Long countByUser(User user);
}
