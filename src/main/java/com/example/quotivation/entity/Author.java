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

    private String description;

    private int quoteCount;

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
}
