package com.example.quotivation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class UserAuthorSubscription extends Timestamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    public UserAuthorSubscription() {}

    public static UserAuthorSubscription make(User user, Author author) {
        return UserAuthorSubscription.builder()
                .user(user)
                .author(author)
                .build();
    }

    public void subscribe() {
        author.increaseSubscriptionCount();
    }

    public void unsubscribe() {
        author.decreaseSubscriptionCount();
    }
}
