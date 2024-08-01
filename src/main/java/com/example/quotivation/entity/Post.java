package com.example.quotivation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Post extends Timestamp {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String content;

    private String writer;

    private String address;

    private int hits;


}
