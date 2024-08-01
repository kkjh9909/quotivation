package com.example.quotivation.controller;

import com.example.quotivation.dto.post.request.PostWriteReq;
import com.example.quotivation.dto.post.response.PostsSummaryRes;
import com.example.quotivation.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    @PostMapping("/post/write")
    @ResponseStatus(value = HttpStatus.OK)
    public void writePost(@RequestBody PostWriteReq postWriteReq, HttpServletRequest request) {
        String ipAddress = getIpAddress(request);

        postService.writePost(postWriteReq, ipAddress);
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty())
            return ip.split(",")[0];

        ip = request.getHeader("X-Real-IP");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        return request.getRemoteAddr();
    }
}
