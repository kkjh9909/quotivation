package com.example.quotivation.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@SequenceGenerator(
        name = "category_sequence",
        sequenceName = "category_id_sequence",
        initialValue = 3000000,
        allocationSize = 1
)
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "category_sequence")
    private Long id;

    private String name;

    private int quoteCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void increaseQuoteCount() {
        this.quoteCount++;
    }
}
