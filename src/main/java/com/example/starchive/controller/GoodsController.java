package com.example.starchive.controller;

import com.example.starchive.config.auth.LoginUser;
import com.example.starchive.dto.GoodsResDto;
import com.example.starchive.dto.ResponseDto.ResponseDto;
import com.example.starchive.dto.TwitterResDto;
import com.example.starchive.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping("/goods")
    public ResponseEntity getTwitter(@AuthenticationPrincipal LoginUser loginUser, Pageable pageable) {
        Page<GoodsResDto> goodsDtoList = goodsService.getData(pageable);
        return new ResponseEntity<>(new ResponseDto<>(1, "정보를 성공적으로 가져왔습니다.", goodsDtoList), HttpStatus.OK);
    }


//    @GetMapping("/goods")
//    public ResponseEntity getGoodsDatas(@RequestBody petLossRequestDto petLossRequestDto, BindingResult bindingResult){
//
//        if(bindingResult.hasErrors()){
//            Map<String, String> errorMap = new HashMap<>();
//            bindingResult.getFieldErrors().forEach(error -> {
//                errorMap.put(error.getField(), error.getDefaultMessage());
//            });
//            return new ResponseEntity<>(new ResponseDto<>(-1, "유효성 검사 실패", errorMap), HttpStatus.BAD_REQUEST);
//        }
//
//        Page<GoodsResDto> goodsResDtos = goodsService.getData()
//
//        return new ResponseEntity(new ResponseDto<>(1, "당신의 결과는 다음과 같습니다.", result), HttpStatus.ACCEPTED);
//    }

}
