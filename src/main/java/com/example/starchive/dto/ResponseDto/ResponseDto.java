package com.example.starchive.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseDto<T> {
    private final Integer code;
    private final String msg;
    private final T data;
}