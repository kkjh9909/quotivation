package com.example.quotivation.dto.author.response;

import com.example.quotivation.entity.UserAuthorSubscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserAuthorSubscriptionResponse {

    private Long id;
    private String name;
    private String picture;
    private String description;

    public static UserAuthorSubscriptionResponse make(UserAuthorSubscription subscription) {
        return UserAuthorSubscriptionResponse.builder()
                .id(subscription.getId())
                .name(subscription.getAuthor().getName())
                .picture(subscription.getAuthor().getPhoto())
                .description(subscription.getAuthor().getDescription())
                .build();
    }
}
