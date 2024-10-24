package com.example.quotivation.service.strategy.user;

import com.example.quotivation.dto.common.SubscriptionCommonResponse;
import org.springframework.data.domain.Pageable;

public interface UserInfoStrategy {

    SubscriptionCommonResponse getSubscriptions(Pageable pageable);
}
