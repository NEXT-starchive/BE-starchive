package com.example.starchive.dto.ResponseDto;

import com.example.starchive.entity.Role;
import com.example.starchive.entity.User;
import com.example.starchive.util.CustomDateUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

public class UserResDto {
    @Getter
    @Setter
    public static class UserLoginResDto {
        private String id;
        private String name;
        private String createdAt;

        public UserLoginResDto(User user) {
            this.id = user.getUserId();
            this.name = user.getName();
            this.createdAt = CustomDateUtil.toStringFormat(user.getCreatedAt());
        }
    }

    @Getter
    @Setter
    @ToString
    public static class UserJoinRespDto {
        private String id;
        private String name;

        public UserJoinRespDto(User user) {
            this.id = user.getUserId();
            this.name = user.getName();
        }
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserInfoResDto {
        private String id;
        private String nickname;
        private String imageUrl;
        private String description;
        private String socialId;
        private Role role;

        @Builder
        public UserInfoResDto(String id, String nickname, String imageUrl, String description, String socialId, Role role) {
            this.id = id;
            this.nickname = nickname;
            this.imageUrl = imageUrl;
            this.description = description;
            this.socialId = socialId;
            this.role = role;
        }
    }
}
