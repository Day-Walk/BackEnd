package com.day_walk.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, false, "USER_001", "유저를 찾을 수 없습니다."),
    USER_AGE_BAD_REQUEST(HttpStatus.BAD_REQUEST, false, "USER_002", "유저의 나이 입력이 잘못되었습니다.");

    private final HttpStatus httpStatus;
    private final boolean success;
    private final String code;
    private final String message;
}
