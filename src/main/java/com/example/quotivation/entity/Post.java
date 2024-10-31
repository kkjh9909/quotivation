package com.example.quotivation.entity;

import com.example.quotivation.dto.post.request.PostWriteRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Post extends Timestamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private int hits;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Post() {}

    public static Post create(PostWriteRequest request, User user) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .hits(0)
                .build();
    }
}
