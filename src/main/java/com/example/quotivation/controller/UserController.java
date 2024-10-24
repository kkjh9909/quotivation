package com.example.quotivation.controller;

import com.example.quotivation.dto.common.SubscriptionCommonResponse;
import com.example.quotivation.dto.user.request.UserSignUpReq;
import com.example.quotivation.dto.user.response.UserInfoResponse;
import com.example.quotivation.service.UserService;
import com.example.quotivation.service.strategy.user.UserInfoStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserInfoStrategyFactory userInfoStrategyFactory;

    @GetMapping("/login")
    public String getLoginPage() {
        return "user/login-page";
    }

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "user/sign-up-page";
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody UserSignUpReq userSignUpReq) {
        userService.signup(userSignUpReq);

        return "redirect:/login";
    }

    @GetMapping("/user/info")
    public String getUserInfoPage(@RequestParam(defaultValue = "author") String type,
                                    Pageable pageable,
                                    Model model) {
        UserInfoResponse userInfo = userService.getUserInfo();

        SubscriptionCommonResponse subscriptions = userInfoStrategyFactory.getStrategy(type).getSubscriptions(pageable);

        model.addAttribute("info", userInfo);
        model.addAttribute("subscription", subscriptions);
        model.addAttribute("type", type);

        return "user/my-page";
    }
}
