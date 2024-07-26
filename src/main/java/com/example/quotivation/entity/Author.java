package com.example.quotivation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Author {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String photo;

    private int quoteCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void increaseQuoteCount() {
        this.quoteCount++;
    }
}