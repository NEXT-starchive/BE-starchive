package com.example.starchive.config.jwt;

import org.springframework.beans.factory.annotation.Value;

public class JwtVO {
    public String SECRET; // HS256 대칭키

    public static String ACCESS_HEADER = "ACCESS_AUTHORIZATION";
    public static String REFRESH_HEADER = "REFRESH_AUTHORIZATION";

    public static int ACCESS_EXPIRATION_TIME = 1000 * 60 * 60 * 2; // 2시간
    public static int REFRESH_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 14; // 2주

    public JwtVO(@Value("${JWT_SECRET_KEY}") String SECRET) {
        this.SECRET = SECRET;
    }
}
