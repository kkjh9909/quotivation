package com.example.quotivation.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignUpReq {
    private String name;
    private String email;
    private String password;
    private String passwordConfirm;
}
