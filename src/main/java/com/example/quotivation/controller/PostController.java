package com.example.quotivation.controller;

import com.example.quotivation.dto.post.response.PostsSummaryRes;
import com.example.quotivation.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public String getPostsPage(Model model,
                               @PageableDefault(sort = {"updatedAt"},
                                       direction = Sort.Direction.DESC,
                                       size = 10)Pageable pageable) {
        PostsSummaryRes response = postService.getAllPosts(pageable);

        if(response.getTotalPages() < pageable.getPageNumber())
            return String.format("redirect:/posts?page=%d", response.getTotalPages());

        model.addAttribute("posts", response.getPosts());
        model.addAttribute("totalPages", response.getTotalPages());
        model.addAttribute("currentPage", pageable.getPageNumber() + 1);

        return "posts-page";
    }

    @GetMapping("/post/write")
    public String getPostWritePage(Model model) {
        return "post-write-page";
    }
}
