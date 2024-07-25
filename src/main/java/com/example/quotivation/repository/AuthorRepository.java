package com.example.quotivation.repository;


import com.example.quotivation.entity.Author;
import com.example.quotivation.entity.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {


    Page<Author> findAllByOrderByQuoteCountDesc(Pageable pageable);

    Page<Author> findAllByOrderByName(Pageable pageable);
}
