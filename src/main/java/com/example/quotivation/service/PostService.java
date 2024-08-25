package com.example.quotivation.service;

import com.example.quotivation.dto.post.request.PostWriteRequest;
import com.example.quotivation.dto.post.response.PostDetailRes;
import com.example.quotivation.dto.post.response.PostsSummaryInfo;
import com.example.quotivation.dto.post.response.PostsSummaryRes;
import com.example.quotivation.dto.post.response.RecentPostsRes;
import com.example.quotivation.entity.Post;
import com.example.quotivation.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostsSummaryRes getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        return new PostsSummaryRes(posts.getTotalPages(), posts.stream().map(PostsSummaryInfo::make).collect(Collectors.toList()));
    }

    public void writePost(PostWriteRequest request) {
        Post post = Post.create(request);

        postRepository.save(post);
    }

    @Transactional
    public PostDetailRes getPostDetail(Long postId) {
        postRepository.increaseHits(postId);

        Optional<Post> post = postRepository.findById(postId);

        return PostDetailRes.make(post.get());
    }

    public List<RecentPostsRes> getRecentPosts(Long postId) {
        List<Post> posts = postRepository.findTop10ByOrderByUpdatedAtDesc();

        return posts.stream()
                .filter(post -> post.getId() != postId)
                .map(RecentPostsRes::make)
                .collect(Collectors.toList());
    }
}
