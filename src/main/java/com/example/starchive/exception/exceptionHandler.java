package com.example.starchive.exception;

import com.example.starchive.dto.ResponseDto.ResponseDto;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;


@RestControllerAdvice
@Log4j2
public class exceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity customExceptionHandler(CustomException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity(new ResponseDto(-1, exception.getMessage(), 0), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    protected ResponseEntity methodnotAllowedExceptionHandler(MethodNotAllowedException exception){
        log.error(exception.getMessage());
        return new ResponseEntity(new ResponseDto(-1, exception.getMessage(), 0), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity httpMethodnotAllowedExceptionHandler(HttpRequestMethodNotSupportedException exception){
        log.error(exception.getMessage());
        return new ResponseEntity(new ResponseDto(-1, exception.getMessage(), 0), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(InvalidFormatException.class)
    protected ResponseEntity invalidFormatExceptionHandler(InvalidFormatException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity(new ResponseDto(-1, "요청된 Request가 format에 맞지 않습니다.", 0), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity nullPointerExceptionHandler(NullPointerException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity(new ResponseDto(-1, exception.getMessage(), 0), HttpStatus.BAD_REQUEST);
    }


}
