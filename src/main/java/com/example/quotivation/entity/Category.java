package com.example.quotivation.entity;

import com.example.quotivation.dto.category.request.AddCategoryRequest;
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
        name = "category_sequence",
        sequenceName = "category_id_sequence",
        initialValue = 3_000_000,
        allocationSize = 1
)
public class Category extends Timestamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "category_sequence")
    private Long id;

    private String name;

    private int quoteCount;

    public Category() {

    }

    public void increaseQuoteCount() {
        this.quoteCount++;
    }

    public static Category make(AddCategoryRequest request) {
        return Category.builder()
                .name(request.getCategory())
                .quoteCount(0)
                .build();
    }
}
