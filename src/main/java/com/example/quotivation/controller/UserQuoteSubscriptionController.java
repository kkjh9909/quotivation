package com.example.quotivation.controller;

import com.example.quotivation.service.UserQuoteSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserQuoteSubscriptionController {

    private final UserQuoteSubscriptionService userQuoteSubscriptionService;

    @PostMapping("/user/quote/subscribe/{quoteId}")
    public String subscribeAuthor(@PathVariable Long quoteId, Model model) {
        userQuoteSubscriptionService.subscribe(quoteId);
        Boolean isSubscribe = userQuoteSubscriptionService.isSubscribe(quoteId);

        model.addAttribute("isSubscribe", isSubscribe);

        return "quote/quote-detail-page :: #subscribeButton";
    }

    @DeleteMapping("/user/quote/subscribe/{quoteId}")
    public String unsubscribeAuthor(@PathVariable Long quoteId, Model model) {
        userQuoteSubscriptionService.unsubscribe(quoteId);
        Boolean isSubscribe = userQuoteSubscriptionService.isSubscribe(quoteId);

        model.addAttribute("isSubscribe", isSubscribe);

        return "quote/quote-detail-page :: #subscribeButton";
    }
}
