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
        name = "quote_like_sequence",
        sequenceName = "quote_like_sequence",
        initialValue = 1_000_000_000,
        allocationSize = 1
)
public class QuoteLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "quote_like_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Quote quote;

    public QuoteLike() {}

    public static QuoteLike make(User user, Quote quote) {
        return QuoteLike.builder()
                .user(user)
                .quote(quote)
                .build();
    }

    public void addLike() {
        quote.increaseLike();
    }

    public void deleteLike() {
        quote.decreaseLike();
    }
}
