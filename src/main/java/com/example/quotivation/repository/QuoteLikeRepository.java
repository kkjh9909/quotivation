package com.example.quotivation.repository;

import com.example.quotivation.entity.QuoteLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuoteLikeRepository extends JpaRepository<QuoteLike, Long> {

    Optional<QuoteLike> findByQuoteIdAndUserId(Long quoteId, long userId);
}
