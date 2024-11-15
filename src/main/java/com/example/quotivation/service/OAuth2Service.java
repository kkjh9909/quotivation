package com.example.quotivation.service;

import com.example.quotivation.oauth.GithubUserInfo;
import com.example.quotivation.security.PrincipalDetails;
import com.example.quotivation.entity.User;
import com.example.quotivation.oauth.GoogleUserInfo;
import com.example.quotivation.oauth.NaverUserInfo;
import com.example.quotivation.oauth.OAuth2UserInfo;
import com.example.quotivation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oauth2Userinfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();

        if(provider.equals("google")){
            oauth2Userinfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if(provider.equals("naver")){
            oauth2Userinfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        } else if(provider.equals("github")) {
            oauth2Userinfo = new GithubUserInfo(oAuth2User.getAttributes());
        }

        Optional<User> user = userRepository.findByProviderAndProviderId(provider, oauth2Userinfo.getProviderId());

        if (user.isEmpty()) {
            User newUser = User.builder()
                    .email(oauth2Userinfo.getEmail())
                    .name(oauth2Userinfo.getName())
                    .provider(oauth2Userinfo.getProvider())
                    .providerId(oauth2Userinfo.getProviderId())
                    .password("OAuth2")
                    .role("ROLE_USER")
                    .picture(provider.equals("naver") ? ((NaverUserInfo) oauth2Userinfo).getProfileImage()
                            : provider.equals("google") ? oAuth2User.getAttribute("picture")
                            : provider.equals("github") ? oAuth2User.getAttribute("avatar_url")
                            : null)
                    .build();

            userRepository.save(newUser);
            return new PrincipalDetails(newUser, oAuth2User.getAttributes());
        } else {
            return new PrincipalDetails(user.get(), oAuth2User.getAttributes());
        }
    }
}
