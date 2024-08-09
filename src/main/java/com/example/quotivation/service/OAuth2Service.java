package com.example.quotivation.service;

import com.example.quotivation.entity.PrincipalDetails;
import com.example.quotivation.entity.User;
import com.example.quotivation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");
        String password = "OAuth2";
        String role = "ROLE_USER";

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            User newUser = User.builder()
                    .email(email)
                    .name(name)
                    .password(password)
                    .role(role)
                    .picture(picture)
                    .build();

            userRepository.save(newUser);
            return new PrincipalDetails(newUser, oAuth2User.getAttributes());
        } else {
            return new PrincipalDetails(user.get(), oAuth2User.getAttributes());
        }
    }
}
