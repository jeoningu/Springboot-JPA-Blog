package com.jig.blog.config.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    private String id;
    private Map<String, Object> attributes; // super.loadUser(userRequest).getAttributes()로 받음

    public KakaoUserInfo(String id, Map<String, Object> attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return id;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public String getName() {
        return (String)attributes.get("name");
    }
}
