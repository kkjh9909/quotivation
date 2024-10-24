package com.example.quotivation.dto.quote.response;

import com.example.quotivation.entity.UserQuoteSubscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserQuoteSubscriptionResponse {

    private Long id;
    private String content;

    public static UserQuoteSubscriptionResponse make(UserQuoteSubscription subscription) {
        return UserQuoteSubscriptionResponse.builder()
                .id(subscription.getId())
                .content(subscription.getQuote().getContent())
                .build();
    }
}
