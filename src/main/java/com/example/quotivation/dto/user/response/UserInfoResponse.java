package com.example.quotivation.dto.user.response;

import com.example.quotivation.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserInfoResponse {

    private Long id;
    private String name;
    private String email;
    private String profileImage;

    public static UserInfoResponse make(User user) {
        return UserInfoResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profileImage(user.getPicture())
                .build();
    }
}
