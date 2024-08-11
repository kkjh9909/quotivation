package com.example.quotivation.dto.user.response;

import lombok.Data;

@Data
public class UserLoginReq {

    private String email;
    private String password;
}
