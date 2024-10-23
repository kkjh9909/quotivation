package com.example.quotivation.controller;

import com.example.quotivation.service.QuoteLikeService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class QuoteLikeController {

    private final QuoteLikeService quoteLikeService;

    @PostMapping("/user/quote/like/{quoteId}")
    public String increaseLike(@PathVariable Long quoteId, Model model) {
        int likeCount = quoteLikeService.increaseLike(quoteId);
        Boolean isLike = quoteLikeService.isLike(quoteId);

        model.addAttribute("likeCount", likeCount);
        model.addAttribute("isLike", isLike);

        return "quote/quote-detail-page :: #likeButton";
    }

    @DeleteMapping("/user/quote/like/{quoteId}")
    public String decreaseLike(@PathVariable Long quoteId, Model model) {
        int likeCount = quoteLikeService.decreaseLike(quoteId);
        Boolean isLike = quoteLikeService.isLike(quoteId);

        model.addAttribute("likeCount", likeCount);
        model.addAttribute("isLike", isLike);

        return "quote/quote-detail-page :: #likeButton";
    }
}
