package com.day_walk.backend.domain.user.data.dto.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class GetUserBySignInDto {
    private UUID userId;
    private String name;
    private String nextPage;

    @Builder
    public GetUserBySignInDto(UUID userId, String name, String nextPage) {
        this.userId = userId;
        this.name = name;
        this.nextPage = nextPage;
    }
}
