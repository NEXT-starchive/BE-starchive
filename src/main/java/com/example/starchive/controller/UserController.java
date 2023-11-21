package com.example.starchive.controller;

import com.example.starchive.config.auth.LoginCode;
import com.example.starchive.config.auth.LoginResDto;
import com.example.starchive.config.auth.LoginUser;
import com.example.starchive.config.auth.SocialUserInfo;
import com.example.starchive.dto.ResponseDto.ResponseDto;
import com.example.starchive.dto.dateDto.DateConvertDto;
import com.example.starchive.service.OAuthService;
import com.example.starchive.service.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final OAuthService oAuthService;

    private final UserService userService;

    @GetMapping("/api/login")
    public ResponseEntity login(@RequestParam String code) {

        // get accessToken using code
        String accessToken = oAuthService.getAccessToken(code);
        // get user information using accessToken
        SocialUserInfo socialUserInfo = oAuthService.getUserInfo(accessToken);
        // save or update oauth information
        LoginResDto.LoginSuccessResDto loginSuccessResDto = oAuthService.saveOrUpdate(socialUserInfo);

        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 성공", loginSuccessResDto), HttpStatus.OK);
    }

    @PostMapping("/firstday")
    public ResponseEntity saveFirstDay(@AuthenticationPrincipal LoginUser loginUser,
                                       @RequestBody DateConvertDto firstday){
        System.out.println(loginUser.getUser().getUserId());
        userService.updateFirstDay(loginUser.getUser().getUserId(), firstday.getFirstday());

        return new ResponseEntity<>(new ResponseDto<>(1, "정보가 성공적으로 저장되었습니다.", null), HttpStatus.OK);
    }

    @GetMapping("/firstday")
    public ResponseEntity getFirstDay(@AuthenticationPrincipal LoginUser loginUser){

        Integer afterDay = userService.getFirstday(loginUser.getUser().getUserId());

        Map<String, Integer> responseData = new HashMap<>();
        responseData.put("firstday", afterDay);

        return new ResponseEntity<>(new ResponseDto<>(1, "정보를 성공적으로 가져왔습니다.", responseData), HttpStatus.OK);
    }

    @PostMapping("/textballon")
    public ResponseEntity saveTextballon(@AuthenticationPrincipal LoginUser loginUser, @RequestBody String textballon){

        userService.updateTextBalloon(loginUser.getUser().getUserId(), textballon);

        return new ResponseEntity<>(new ResponseDto<>(1, "정보가 성공적으로 저장되었습니다.", null), HttpStatus.OK);
    }

    @GetMapping("/textballon")
    public ResponseEntity getTextballon(@AuthenticationPrincipal LoginUser loginUser){

        String textBallon = userService.getTextBalloon(loginUser.getUser().getUserId());

        Map<String, String> responseData = new HashMap<>();
        responseData.put("textballon", textBallon);

        return new ResponseEntity<>(new ResponseDto<>(1, "정보를 성공적으로 가져왔습니다.", responseData), HttpStatus.OK);
    }
}
