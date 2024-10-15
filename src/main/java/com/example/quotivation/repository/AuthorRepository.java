package com.example.quotivation.repository;


import com.example.quotivation.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {


    Page<Author> findAllByOrderByQuoteCountDesc(Pageable pageable);

    Page<Author> findAllByOrderByName(Pageable pageable);

    List<Author> findByNameContainingOrderByCreatedAtDesc(String query, Pageable pageable);

    Long countByNameContaining(String query);
}
