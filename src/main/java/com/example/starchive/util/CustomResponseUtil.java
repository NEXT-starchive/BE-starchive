package com.example.starchive.util;


import com.example.starchive.dto.ResponseDto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CustomResponseUtil {

    public static void success(HttpServletResponse response, Object dto){
        try {
            ObjectMapper mapper = new ObjectMapper();
            ResponseDto<Object> responseDto = new ResponseDto<>(1, "로그인 성공", dto);
            String responseBody = mapper.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(200);
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }

    public static void fail(HttpServletResponse response, String msg, HttpStatus httpStatus){
        try {
            ObjectMapper mapper = new ObjectMapper();
            ResponseDto<Object> responseDto = new ResponseDto<>(-1, msg, null);
            String responseBody = mapper.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(httpStatus.value());
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }

}
