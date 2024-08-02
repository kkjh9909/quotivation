package com.example.quotivation.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@SequenceGenerator(
        name = "quote_sequence",
        sequenceName = "quote_id_sequence",
        initialValue = 50000000,
        allocationSize = 1
)
public class Quote {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "quote_sequence")
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Author author;

    public Quote() {}

    public static Quote create(Category category, Author author, String content) {
        Quote quote = new Quote();

        quote.content = content;
        LocalDateTime time = LocalDateTime.now();

        quote.createdAt = time;
        quote.updatedAt = time;

        quote.category = category;
        quote.author = author;

        return quote;
    }
}
