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

    @Builder
    public GetUserBySignInDto(UUID userId, Long kakaoId, String name, String nextPage) {
        this.userId = userId;
        this.kakaoId = kakaoId;
        this.name = name;
        this.nextPage = nextPage;
    }
}
