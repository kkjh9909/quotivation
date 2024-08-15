package com.example.quotivation.controller;

import com.example.quotivation.service.QuoteDislikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class QuoteDislikeController {

    private final QuoteDislikeService quoteDislikeService;

    @PostMapping("/user/quote/dislike/{quoteId}")
    public String increaseDislike(@PathVariable Long quoteId, Model model) {
        int likeCount = quoteDislikeService.increaseDislike(quoteId);
        Boolean isLike = quoteDislikeService.isLike(quoteId);

        model.addAttribute("dislikeCount", likeCount);
        model.addAttribute("isDislike", isLike);

        return "quote-detail-page :: #dislikeButton";
    }

    @DeleteMapping("/user/quote/dislike/{quoteId}")
    public String decreaseDislike(@PathVariable Long quoteId, Model model) {
        int likeCount = quoteDislikeService.decreaseDislike(quoteId);
        Boolean isLike = quoteDislikeService.isLike(quoteId);

        model.addAttribute("dislikeCount", likeCount);
        model.addAttribute("isDislike", isLike);

        return "quote-detail-page :: #dislikeButton";
    }
}
