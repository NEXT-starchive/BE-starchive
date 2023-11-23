package com.example.starchive.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.starchive.exception.CustomForbiddenException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProcess {

    @Value("${JWT_SECRET_KEY}")
    private String JWT_SECRET_KEY;

    public String createJWT(String id, String userRole, String username, TokenType type) {
        String jwt = JWT.create()
                .withSubject("jariBean")
                .withExpiresAt(new Date(System.currentTimeMillis() + type.getTime()))
                .withClaim("userId", id)
                .withClaim("userRole", userRole)
                .withClaim("username", username)
                .sign(Algorithm.HMAC512(JWT_SECRET_KEY));

        return jwt;
    }


    // verify JWT (return 되는 LoginUser 객체를 강제로 시큐리티 세션에 직접 주입할 예정)
    public JwtDto verify(String jwt) {

        DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC512(JWT_SECRET_KEY)).build().verify(jwt);

        // 토큰 만료기간 검증
        Date expiresAt = decodedJwt.getExpiresAt();
        if(expiresAt.before(new Date())) {
            throw new CustomForbiddenException("토큰 사용기간이 만료되었습니다.");
        }

        String id = decodedJwt.getClaim("userId").asString();
        String userRole = decodedJwt.getClaim("userRole").asString();
        JwtDto jwtDto = new JwtDto(id, userRole);

        return jwtDto;

    }

    @Getter
    @AllArgsConstructor
    public enum TokenType {
        ACCESS(1000 * 60 * 60 * 2), REFRESH(1000 * 60 * 60 * 24 * 14);
        private int time;
    }
}
