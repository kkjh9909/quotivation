package com.example.quotivation.dto.author.response;

import com.example.quotivation.dto.common.SubscriptionCommonResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserAuthorSubscriptionListResponse implements SubscriptionCommonResponse {

    private List<UserAuthorSubscriptionResponse> subscriptions;
    private Long totalCount;
}
