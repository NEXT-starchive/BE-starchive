package com.example.starchive.controller;

import com.example.starchive.config.auth.LoginCode;
import com.example.starchive.config.auth.LoginResDto;
import com.example.starchive.config.auth.SocialUserInfo;
import com.example.starchive.dto.ResponseDto.ResponseDto;
import com.example.starchive.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final OAuthService oAuthService;

    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody LoginCode loginCode) {
        // assign an oauthService corresponding to the registrationId

        // get accessToken using code
        String accessToken = oAuthService.getAccessToken(loginCode.getCode());
        // get user information using accessToken
        SocialUserInfo socialUserInfo = oAuthService.getUserInfo(accessToken);
        // save or update oauth information
        LoginResDto.LoginSuccessResDto loginSuccessResDto = oAuthService.saveOrUpdate(socialUserInfo);

        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 성공", loginSuccessResDto), HttpStatus.OK);
    }
}
