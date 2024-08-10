package com.example.quotivation.dto.user.request;

import lombok.Data;

@Data
public class UserSignUpReq {
    private String name;
    private String email;
    private String password;
    private String passwordConfirm;
}
