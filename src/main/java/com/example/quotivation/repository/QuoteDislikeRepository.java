package com.example.quotivation.repository;

import com.example.quotivation.entity.QuoteDislike;
import com.example.quotivation.entity.QuoteLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuoteDislikeRepository extends JpaRepository<QuoteDislike, Long> {

    Optional<QuoteDislike> findByQuoteIdAndUserId(Long quoteId, long userId);
}
