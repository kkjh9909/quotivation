package com.example.quotivation.controller;

import com.example.quotivation.dto.user.request.UserSignUpReq;
import com.example.quotivation.dto.user.response.UserLoginReq;
import com.example.quotivation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login-page";
    }

//    @PostMapping("/login")
//    public String login(UserLoginReq request) {
//        userService.login(request);
//    }

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "sign-up-page";
    }

    @PostMapping("/signup")
    public String signUp(UserSignUpReq userSignUpReq) {
        userService.signup(userSignUpReq);

        return "redirect:/login";
    }
}
