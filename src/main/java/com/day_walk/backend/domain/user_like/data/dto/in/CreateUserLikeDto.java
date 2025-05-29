package com.day_walk.backend.domain.user_like.data.dto.in;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class CreateUserLikeDto {
    private UUID userId;
}
