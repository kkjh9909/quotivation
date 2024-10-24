package com.example.quotivation.repository;

import com.example.quotivation.dto.quote.response.UserQuoteSubscriptionResponse;
import com.example.quotivation.entity.Quote;
import com.example.quotivation.entity.User;
import com.example.quotivation.entity.UserQuoteSubscription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserQuoteSubscriptionRepository extends JpaRepository<UserQuoteSubscription, Long> {
    Optional<UserQuoteSubscription> findByUserAndQuote(User user, Quote quote);

    List<UserQuoteSubscription> findByUser(User user, Pageable pageable);

    Long countByUser(User user);
}
