package com.example.quotivation.oauth;

import com.example.quotivation.entity.PrincipalDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String token = jwtProvider.createAccessToken(principalDetails.getUser());

        Cookie authCookie = new Cookie("access_token", token);
        authCookie.setHttpOnly(true);
//        authCookie.setSecure(true);  // https 설정시
        authCookie.setPath("/");
        authCookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(authCookie);

        String redirectUrl = "/";
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
