package com.example.quotivation.repository;

import com.example.quotivation.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByOrderByQuoteCountDesc(Pageable pageable);

    Page<Category> findAllByOrderByName(Pageable pageable);
}
