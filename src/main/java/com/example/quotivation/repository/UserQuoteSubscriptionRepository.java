package com.example.quotivation.repository;

import com.example.quotivation.entity.UserQuoteSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserQuoteSubscriptionRepository extends JpaRepository<UserQuoteSubscription, Long> {
    Optional<UserQuoteSubscription> findByUserIdAndQuoteId(Long userId, Long quoteId);
}
