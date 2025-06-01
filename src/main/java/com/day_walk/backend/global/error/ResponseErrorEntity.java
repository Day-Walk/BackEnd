package com.day_walk.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResponseErrorEntity {
    private boolean success;
    private String message;
    private String name;
    private String code;
    private LocalDateTime time;

    public static ResponseEntity<ResponseErrorEntity> toResponseEntity(ErrorCode e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ResponseErrorEntity.builder()
                        .success(e.isSuccess())
                        .message(e.getMessage())
                        .name(e.name())
                        .code(e.getCode())
                        .time(LocalDateTime.now())
                        .build());
    }
}
