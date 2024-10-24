package com.example.quotivation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class UserQuoteSubscription extends Timestamp {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Quote quote;

    public UserQuoteSubscription() {}

    public static UserQuoteSubscription make(User user, Quote quote) {
        return UserQuoteSubscription.builder()
                .user(user)
                .quote(quote)
                .build();
    }

    public void subscribe() {
        this.quote.increaseSubscriptionCount();
    }

    public void unsubscribe() {
        this.quote.decreaseSubscriptionCount();
    }
}
