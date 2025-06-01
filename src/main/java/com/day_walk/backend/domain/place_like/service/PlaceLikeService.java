package com.day_walk.backend.domain.place_like.service;

import com.day_walk.backend.domain.place_like.bean.DeletePlaceLikeEntityBean;
import com.day_walk.backend.domain.place_like.bean.GetPlaceLikeEntityBean;
import com.day_walk.backend.domain.place_like.bean.SavePlaceLikeEntityBean;
import com.day_walk.backend.domain.place_like.data.PlaceLikeEntity;
import com.day_walk.backend.domain.place_like.data.in.DeletePlaceLikeDto;
import com.day_walk.backend.domain.place_like.data.in.SavePlaceLikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PlaceLikeService {
    private final SavePlaceLikeEntityBean savePlaceLikeEntityBean;
    private final GetPlaceLikeEntityBean getPlaceLikeEntityBean;
    private final DeletePlaceLikeEntityBean deletePlaceLikeEntityBean;

    public UUID savePlaceLike(SavePlaceLikeDto savePlaceLikeDto) {
        PlaceLikeEntity placeLike = PlaceLikeEntity.builder()
                .savePlaceLikeDto(savePlaceLikeDto)
                .build();

        savePlaceLikeEntityBean.exec(placeLike);

        return placeLike.getPlaceId();
    }

    public boolean deletePlaceLike(DeletePlaceLikeDto deletePlaceLikeDto) {
        PlaceLikeEntity placeLike = getPlaceLikeEntityBean.exec(deletePlaceLikeDto.getPlaceLikeId());
        if (placeLike == null) {
            return false;
        }

        deletePlaceLikeEntityBean.exec(placeLike);

        return true;
    }
}
