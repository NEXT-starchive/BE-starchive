package com.example.starchive.config.auth;

import lombok.Getter;

@Getter
public class SocialUserInfo {
    private String socialId;
    private String nickname;
    private String imageUrl;

    public static SocialUserInfo create(String registration, String id, String nickname, String imageUrl) {
        SocialUserInfo socialUserInfo = new SocialUserInfo();
        socialUserInfo.socialId = registration + "_" + id;
        socialUserInfo.nickname = nickname;
        socialUserInfo.imageUrl = imageUrl;
        return socialUserInfo;
    }
}