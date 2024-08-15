package com.example.quotivation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@SequenceGenerator(
        name = "quote_dislike_sequence",
        sequenceName = "quote_dislike_sequence",
        initialValue = 2_000_000_000,
        allocationSize = 1
)
public class QuoteDislike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "quote_dislike_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Quote quote;

    public QuoteDislike() {}

    public static QuoteDislike make(User user, Quote quote) {
        return QuoteDislike.builder()
                .user(user)
                .quote(quote)
                .build();
    }

    public void addDislike() {
        quote.increaseDislike();
    }

    public void deleteDislike() {
        quote.decreaseDislike();
    }
}
