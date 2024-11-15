package com.example.quotivation.entity;

import com.example.quotivation.dto.user.request.UserSignUpReq;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class User extends Timestamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    private String email;

    private String role;

    private String picture;

    private String provider;

    private String providerId;

    public User() {}

    @Builder
    public User(String name, String password, String email, String provider, String providerId, String role, String picture) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.picture = picture;
    }

    public static User createUser(UserSignUpReq request, String password) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(password)
                .role("ROLE_USER")
                .build();
    }
}
