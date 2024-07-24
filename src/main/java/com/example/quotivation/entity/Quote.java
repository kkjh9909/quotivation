package com.example.quotivation.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Quote {

    @Id @GeneratedValue
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Author author;
}
