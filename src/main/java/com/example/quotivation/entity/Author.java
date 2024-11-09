package com.example.quotivation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Author extends Timestamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String photo;

    private String description;

    private int quoteCount;

    private int subscriptionCount;

    public Author() {}

    public void increaseQuoteCount() {
        this.quoteCount++;
    }

    public static Author make(String name, String description, String imageUrl) {
        return Author.builder()
                .name(name)
                .description(description)
                .photo(imageUrl)
                .quoteCount(0)
                .build();
    }

    public void increaseSubscriptionCount() {
        this.subscriptionCount++;
    }

    public void decreaseSubscriptionCount() {
        this.subscriptionCount--;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
