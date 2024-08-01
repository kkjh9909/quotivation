package com.example.quotivation.service;

import com.example.quotivation.dto.post.response.PostsSummaryInfo;
import com.example.quotivation.dto.post.response.PostsSummaryRes;
import com.example.quotivation.entity.Post;
import com.example.quotivation.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostsSummaryRes getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        return new PostsSummaryRes(posts.getTotalPages(), posts.stream().map(PostsSummaryInfo::make).collect(Collectors.toList()));
    }
}
