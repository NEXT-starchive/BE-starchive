package com.example.starchive.service;

import com.example.starchive.config.auth.GoogleOAuthInfo;
import com.example.starchive.config.auth.GoogleUserInfo;
import com.example.starchive.config.auth.LoginResDto;
import com.example.starchive.config.auth.LoginResDto.*;
import com.example.starchive.config.auth.SocialUserInfo;
import com.example.starchive.config.jwt.JwtProcess;
import com.example.starchive.entity.Role;
import com.example.starchive.entity.User;
import com.example.starchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OAuthService {

    @Value("${GOOGLE_CLIENT_ID}")
    private String GOOGLE_CLIENT_ID;

    @Value("${GOOGLE_CLIENT_SECRET}")
    private String GOOGLE_CLIENT_SECRET;

    @Value(("${GOOGLE_REDIRECT_URI}"))
    private String GOOGLE_REDIRECT_URI;

    @Value(("${GOOGLE_REDIRECT_URI_TEST}"))
    private String GOOGLE_REDIRECT_URI_TEST;

    protected final UserRepository userRepository;
    private final JwtProcess jwtProcess;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Test
    public String getTestAccessToken(String code) {

        MultiValueMap<String, String> bodyValue = new LinkedMultiValueMap<>();
        bodyValue.add("grant_type", "authorization_code");
        bodyValue.add("client_id", GOOGLE_CLIENT_ID);
        bodyValue.add("client_secret", GOOGLE_CLIENT_SECRET);
        bodyValue.add("redirect_uri", GOOGLE_REDIRECT_URI_TEST);
        bodyValue.add("code", code);

        WebClient client = WebClient.create();
        return client.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(bodyValue)
                .retrieve()
                .bodyToMono(GoogleOAuthInfo.class)
                .block()
                .getAccess_token();
    }


    public String getAccessToken(String code) {

        MultiValueMap<String, String> bodyValue = new LinkedMultiValueMap<>();
        bodyValue.add("grant_type", "authorization_code");
        bodyValue.add("client_id", GOOGLE_CLIENT_ID);
        bodyValue.add("client_secret", GOOGLE_CLIENT_SECRET);
        bodyValue.add("redirect_uri", GOOGLE_REDIRECT_URI);
        bodyValue.add("code", code);

        WebClient client = WebClient.create();
        return client.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(bodyValue)
                .retrieve()
                .bodyToMono(GoogleOAuthInfo.class)
                .block()
                .getAccess_token();
    }

    public LoginSuccessResDto renewJWT(String userId, String username, Role role, String refreshJWT) {

        String accessToken = jwtProcess.createJWT(userId, role.getRole(), username, JwtProcess.TokenType.ACCESS);

        String refreshToken = jwtProcess.createJWT(userId, role.getRole(), username, JwtProcess.TokenType.REFRESH);

        return LoginSuccessResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public LoginSuccessResDto saveOrUpdate(SocialUserInfo socialUserInfo) {

        // save or update user
        User savedUser = saveOrUpdateUser(socialUserInfo);

        //create JWT
        String accessToken = jwtProcess.createJWT(savedUser.getUserId(), savedUser.getRole().toString(), savedUser.getName(), JwtProcess.TokenType.ACCESS);
        String refreshToken = jwtProcess.createJWT(savedUser.getUserId(), savedUser.getRole().toString(), savedUser.getName(), JwtProcess.TokenType.REFRESH);

        // storing jwt in redis
        // saveToken(savedUser.getId(), accessToken, refreshToken);

        return LoginSuccessResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    public SocialUserInfo getUserInfo(String accessToken) {
        WebClient client1 = WebClient.create();
        GoogleUserInfo userInfo = client1.get()
                .uri("https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=" + accessToken)
                .retrieve()
                .bodyToMono(GoogleUserInfo.class)
                .block();

        return SocialUserInfo.create(
                "Google",
                userInfo.getId(),
                userInfo.getName() != null ? userInfo.getName() : "Guest",
                userInfo.getPicture() != null ? userInfo.getPicture() : "https://img.jari-bean.com/a0155280-ad92-4a29-9965-8f41b2aad98dVector.png"
        );
    }

    private User saveOrUpdateUser(SocialUserInfo socialUserInfo) {
        // save or create user
        User user = userRepository.findByUserId(socialUserInfo.getSocialId())
                .orElse(User.builder()
                        .userId(socialUserInfo.getSocialId())
                        .name(socialUserInfo.getNickname())
                        .textballoon("")
                        .firstday(LocalDateTime.now())
                        .role(Role.UNREGISTERED)
                        .build());
        return userRepository.save(user);
    }


}
