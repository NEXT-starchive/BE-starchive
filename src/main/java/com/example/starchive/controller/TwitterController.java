package com.example.starchive.controller;


import com.example.starchive.config.auth.LoginUser;
import com.example.starchive.dto.ResponseDto.ResponseDto;
import com.example.starchive.dto.TwitterResDto;
import com.example.starchive.dto.YoutubeResDto;
import com.example.starchive.service.TwitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TwitterController {

    private final TwitterService twitterService;

    @GetMapping("/twitter")
    public ResponseEntity getTwitter(@AuthenticationPrincipal LoginUser loginUser, Pageable pageable) {
        Page<TwitterResDto> goodsDtoList = twitterService.getData(pageable);
        return new ResponseEntity<>(new ResponseDto<>(1, "정보를 성공적으로 가져왔습니다.", goodsDtoList), HttpStatus.OK);
    }
}
