package com.example.quotivation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login-page";
    }

    @GetMapping("/private")
    public String getLoginPage1() {
        return "private-page";
    }
}
