package com.day_walk.backend.domain.place_like.bean;

import com.day_walk.backend.domain.place_like.data.PlaceLikeEntity;
import com.day_walk.backend.domain.place_like.repository.PlaceLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GetPlaceLikeEntityBean {
    private final PlaceLikeRepository placeLikeRepository;

    public PlaceLikeEntity exec(UUID placeLikeId) {
        return placeLikeRepository.findById(placeLikeId).orElse(null);
    }
}
