package com.example.quotivation.repository.api;

import com.example.quotivation.entity.Category;
import com.example.quotivation.entity.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteApiRepository extends JpaRepository<Quote, Long> {

    Page<Quote> findByCategory(Category category, Pageable pageable);
}
