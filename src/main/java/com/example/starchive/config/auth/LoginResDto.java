package com.example.starchive.config.auth;

import lombok.Builder;
import lombok.Data;

public class LoginResDto {

    @Data
    public static class LoginSuccessResDto {
        private String accessToken;
        private String refreshToken;

        @Builder
        public LoginSuccessResDto(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
