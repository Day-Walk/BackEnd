package com.day_walk.backend.domain.user_like.data.dto.out;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class GetUserLikeDto {

    private UUID userId;
}
