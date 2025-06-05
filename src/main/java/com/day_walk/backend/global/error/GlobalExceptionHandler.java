package com.day_walk.backend.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseErrorEntity> handleCustomException(CustomException e) {
        return ResponseErrorEntity.toResponseEntity(e.errorCode);
    }
}
