package com.example.quotivation.repository;

import com.example.quotivation.entity.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    Page<Quote> findByOrderByUpdatedAtDesc(Pageable pageable);
}
