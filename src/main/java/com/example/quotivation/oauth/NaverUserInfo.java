package com.example.quotivation.oauth;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getPhoneNumber() {
        return null;
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    public String getProfileImage() {
        return (String) attributes.get("profile_image");
    }
}
