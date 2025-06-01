package com.day_walk.backend.domain.place_like.bean;

import com.day_walk.backend.domain.place_like.data.PlaceLikeEntity;
import com.day_walk.backend.domain.place_like.repository.PlaceLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SavePlaceLikeEntityBean {
    private final PlaceLikeRepository placeLikeRepository;

    public void exec(PlaceLikeEntity placeLike) {
        placeLikeRepository.save(placeLike);
    }
}
