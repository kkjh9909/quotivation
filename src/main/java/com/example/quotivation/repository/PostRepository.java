package com.example.quotivation.repository;

import com.example.quotivation.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying
    @Query("update Post p set p.hits = p.hits + 1 where p.id = :postId")
    int increaseHits(Long postId);

    List<Post> findTop10ByOrderByUpdatedAtDesc();
}
