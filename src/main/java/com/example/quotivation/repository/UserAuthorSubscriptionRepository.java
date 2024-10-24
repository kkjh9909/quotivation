package com.example.quotivation.repository;

import com.example.quotivation.entity.UserAuthorSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthorSubscriptionRepository extends JpaRepository<UserAuthorSubscription, Long> {
    Optional<UserAuthorSubscription> findByUserIdAndAuthorId(Long userId, Long authorId);
}
