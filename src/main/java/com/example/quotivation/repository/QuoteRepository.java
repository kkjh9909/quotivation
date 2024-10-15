package com.example.quotivation.repository;

import com.example.quotivation.entity.Author;
import com.example.quotivation.entity.Category;
import com.example.quotivation.entity.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    Page<Quote> findByOrderByUpdatedAtDesc(Pageable pageable);

    Page<Quote> findByAuthor(Author author, Pageable pageable);

    Page<Quote> findByCategory(Category category, Pageable pageable);

    Page<Quote> findByContentContaining(String query, Pageable pageable);

    @Query("select q from Quote q join fetch q.author where q.category = :category order by q.updatedAt desc")
    List<Quote> findTop10ByCategoryOrderByUpdatedAtDesc(Category category, Pageable pageable);

    List<Quote> findByContentContainingOrderByCreatedAtDesc(String query, Pageable pageable);

    Long countByContentContaining(String query);
}
