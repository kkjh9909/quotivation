package com.example.quotivation.controller;

import com.example.quotivation.service.UserAuthorSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserAuthorSubscriptionController {

    private final UserAuthorSubscriptionService userAuthorSubscriptionService;

    @PostMapping("/user/author/subscribe/{authorId}")
    public String subscribeAuthor(@PathVariable Long authorId, Model model) {
        userAuthorSubscriptionService.subscribe(authorId);
        Boolean isSubscribe = userAuthorSubscriptionService.isSubscribe(authorId);

        model.addAttribute("isSubscribe", isSubscribe);

        return "author/author-detail-page :: #subscribeButton";
    }

    @DeleteMapping("/user/author/subscribe/{authorId}")
    public String unsubscribeAuthor(@PathVariable Long authorId, Model model) {
        userAuthorSubscriptionService.unsubscribe(authorId);
        Boolean isSubscribe = userAuthorSubscriptionService.isSubscribe(authorId);

        model.addAttribute("isSubscribe", isSubscribe);

        return "author/author-detail-page :: #subscribeButton";
    }
}
