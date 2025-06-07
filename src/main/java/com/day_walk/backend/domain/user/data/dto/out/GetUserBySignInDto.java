package com.day_walk.backend.domain.user.data.dto.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUserBySignInDto {
    private UUID userId;
    private Long kakaoId;
    private String name;
    private String nextPage;
    private int gender;
    private int age;

    @Builder
    public GetUserBySignInDto(UUID userId, Long kakaoId, String name, String nextPage, int gender, int age) {
        this.userId = userId;
        this.kakaoId = kakaoId;
        this.name = name;
        this.nextPage = nextPage;
        this.gender = gender;
        this.age = age;
    }
}
