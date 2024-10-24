package com.example.quotivation.dto.quote.response;

import com.example.quotivation.dto.common.SubscriptionCommonResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserQuoteSubscriptionListResponse implements SubscriptionCommonResponse {

    private List<UserQuoteSubscriptionResponse> subscriptions;
    private Long totalCount;
}
