package com.day_walk.backend.domain.user.data.in;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignInUserDto {
    private Long kakaoId;
    private String name;
}
