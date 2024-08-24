package com.example.quotivation.controller;

import com.example.quotivation.dto.user.request.UserSignUpReq;
import com.example.quotivation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login-page";
    }

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "sign-up-page";
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody UserSignUpReq userSignUpReq) {
        userService.signup(userSignUpReq);

        return "redirect:/login";
    }
}
