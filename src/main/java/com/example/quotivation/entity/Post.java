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
@SequenceGenerator(
        name = "post_sequence",
        sequenceName = "post_id_sequence",
        initialValue = 10000000,
        allocationSize = 1
)
public class Post extends Timestamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "post_sequence")
    private Long id;

    private String title;

    private String content;

    private int hits;

    public Post() {}

    public static Post create(PostWriteRequest request) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .hits(0)
                .build();
    }
}
