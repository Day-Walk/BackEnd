package com.day_walk.backend.domain.place_like.data.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlaceLikeEvent {
    private UUID userId;
    private UUID placeId;
    private boolean liked;
}
