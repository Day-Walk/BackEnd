package com.day_walk.backend.domain.place_like.bean;

import com.day_walk.backend.domain.place_like.data.PlaceLikeEntity;
import com.day_walk.backend.domain.place_like.repository.PlaceLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GetPlaceLikeEntityBean {
    private final PlaceLikeRepository placeLikeRepository;

    public List<PlaceLikeEntity> exec(UUID userId) {
        return placeLikeRepository.findAllByUserId(userId);
    }

    public PlaceLikeEntity exec(UUID userId, UUID placeId) {
        return placeLikeRepository.findByUserIdAndPlaceId(userId, placeId);
    }
}
