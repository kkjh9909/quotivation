package com.example.quotivation.entity;

import com.example.quotivation.dto.user.request.UserSignUpReq;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@SequenceGenerator(
        name = "user_sequence",
        sequenceName = "user_id_sequence",
        initialValue = 100_000_000,
        allocationSize = 1
)
public class User extends Timestamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_sequence")
    private Long id;

    private String name;

    private String password;

    private String email;

    private String role;

    private String picture;

    public User() {}

    @Builder
    public User(String name, String password, String email, String role, String picture) {
        this.name = name;
        this.password = password;
        this.email = email;
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
