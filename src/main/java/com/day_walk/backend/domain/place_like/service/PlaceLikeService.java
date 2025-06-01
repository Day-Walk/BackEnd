package com.day_walk.backend.domain.place_like.service;

import com.day_walk.backend.domain.place_like.bean.SavePlaceLikeEntityBean;
import com.day_walk.backend.domain.place_like.data.PlaceLikeEntity;
import com.day_walk.backend.domain.place_like.data.in.SavePlaceLikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PlaceLikeService {
    private final SavePlaceLikeEntityBean savePlaceLikeEntityBean;

    public UUID savePlaceLike(SavePlaceLikeDto savePlaceLikeDto) {
        PlaceLikeEntity placeLike = PlaceLikeEntity.builder()
                .savePlaceLikeDto(savePlaceLikeDto)
                .build();

        savePlaceLikeEntityBean.exec(placeLike);

        return placeLike.getPlaceId();
    }
}
