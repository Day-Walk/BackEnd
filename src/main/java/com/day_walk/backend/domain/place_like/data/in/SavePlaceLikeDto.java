package com.day_walk.backend.domain.place_like.data.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SavePlaceLikeDto {
    private UUID userId;
    private UUID placeId;
}
