package com.example.quotivation.service.strategy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserInfoStrategyFactory {

    private final Map<String, UserInfoStrategy> strategies;

    public UserInfoStrategy getStrategy(String type) {
        return strategies.getOrDefault("subscription_" + type.toLowerCase(), strategies.get("subscription_author"));
    }
}
