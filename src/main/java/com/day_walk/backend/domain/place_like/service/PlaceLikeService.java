package com.day_walk.backend.domain.place_like.service;

import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.out.GetPlaceByLikeDto;
import com.day_walk.backend.domain.place_like.bean.DeletePlaceLikeEntityBean;
import com.day_walk.backend.domain.place_like.bean.GetPlaceLikeEntityBean;
import com.day_walk.backend.domain.place_like.bean.SavePlaceLikeEntityBean;
import com.day_walk.backend.domain.place_like.data.PlaceLikeEntity;
import com.day_walk.backend.domain.place_like.data.in.DeletePlaceLikeDto;
import com.day_walk.backend.domain.place_like.data.in.SavePlaceLikeDto;
import com.day_walk.backend.global.util.page.PageDto;
import com.day_walk.backend.global.util.page.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlaceLikeService {
    private final SavePlaceLikeEntityBean savePlaceLikeEntityBean;
    private final GetPlaceLikeEntityBean getPlaceLikeEntityBean;
    private final DeletePlaceLikeEntityBean deletePlaceLikeEntityBean;
    private final GetPlaceEntityBean getPlaceEntityBean;

    public UUID savePlaceLike(SavePlaceLikeDto savePlaceLikeDto) {
        PlaceLikeEntity placeLike = PlaceLikeEntity.builder()
                .savePlaceLikeDto(savePlaceLikeDto)
                .build();

        savePlaceLikeEntityBean.exec(placeLike);

        return placeLike.getPlaceId();
    }

    public boolean deletePlaceLike(DeletePlaceLikeDto deletePlaceLikeDto) {
        PlaceLikeEntity placeLike = getPlaceLikeEntityBean.exec(deletePlaceLikeDto);
        if (placeLike == null) {
            return false;
        }

        deletePlaceLikeEntityBean.exec(placeLike);

        return true;
    }

    public List<PageDto<GetPlaceByLikeDto>> getPlaceLike(UUID userId) {
        List<PlaceLikeEntity> placeLikeList = getPlaceLikeEntityBean.exec(userId);
        if (placeLikeList == null || placeLikeList.isEmpty()) {
            return Collections.emptyList();
        }

        List<GetPlaceByLikeDto> placeByLikeDtoList = placeLikeList.stream()
                .map(placeLike -> GetPlaceByLikeDto.builder()
                        .place(getPlaceEntityBean.exec(placeLike.getPlaceId()))
                        .build())
                .toList();

        return PaginationUtil.paginate(placeByLikeDtoList, 10);
    }
}
