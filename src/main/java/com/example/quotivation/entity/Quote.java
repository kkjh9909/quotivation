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
public class Quote extends Timestamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private int likeCount;

    private int dislikeCount;

    private int subscriptionCount;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Author author;

    public Quote() {}

    public static Quote create(Category category, Author author, String content) {
        return Quote.builder()
                .category(category)
                .author(author)
                .content(content)
                .likeCount(0)
                .build();
    }

    public void increaseLike() {
        this.likeCount++;
    }

    public void decreaseLike() {
        this.likeCount--;
    }

    public void increaseDislike() {
        this.dislikeCount++;
    }

    public void decreaseDislike() {
        this.dislikeCount--;
    }

    public void increaseSubscriptionCount() {
        this.subscriptionCount++;
    }

    public void decreaseSubscriptionCount() {
        this.subscriptionCount--;
    }
}
