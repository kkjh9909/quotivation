package com.example.quotivation.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@SequenceGenerator(
        name = "author_sequence",
        sequenceName = "author_id_sequence",
        initialValue = 2000000,
        allocationSize = 1
)
public class Author extends Timestamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "author_sequence")
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
