package com.day_walk.backend.domain.place_like.service;

import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.data.out.GetPlaceByLikeDto;
import com.day_walk.backend.domain.place_like.bean.DeletePlaceLikeEntityBean;
import com.day_walk.backend.domain.place_like.bean.GetPlaceLikeEntityBean;
import com.day_walk.backend.domain.place_like.bean.SavePlaceLikeEntityBean;
import com.day_walk.backend.domain.place_like.data.PlaceLikeEntity;
import com.day_walk.backend.domain.place_like.data.in.DeletePlaceLikeDto;
import com.day_walk.backend.domain.place_like.data.in.SavePlaceLikeDto;
import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import com.day_walk.backend.global.util.page.PageDto;
import com.day_walk.backend.global.util.page.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PlaceLikeService {
    private final GetUserEntityBean getUserEntityBean;
    private final SavePlaceLikeEntityBean savePlaceLikeEntityBean;
    private final GetPlaceLikeEntityBean getPlaceLikeEntityBean;
    private final DeletePlaceLikeEntityBean deletePlaceLikeEntityBean;
    private final GetPlaceEntityBean getPlaceEntityBean;

    public boolean savePlaceLike(SavePlaceLikeDto savePlaceLikeDto) {
        UserEntity user = getUserEntityBean.exec(savePlaceLikeDto.getUserId());
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        PlaceEntity place = getPlaceEntityBean.exec(savePlaceLikeDto.getPlaceId());
        if (place == null) {
            throw new CustomException(ErrorCode.PLACE_NOT_FOUND);
        }

        PlaceLikeEntity placeLike = PlaceLikeEntity.builder()
                .savePlaceLikeDto(savePlaceLikeDto)
                .build();

        savePlaceLikeEntityBean.exec(placeLike);

        return true;
    }

    public boolean deletePlaceLike(DeletePlaceLikeDto deletePlaceLikeDto) {
        UserEntity user = getUserEntityBean.exec(deletePlaceLikeDto.getUserId());
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        PlaceEntity place = getPlaceEntityBean.exec(deletePlaceLikeDto.getPlaceId());
        if (place == null) {
            throw new CustomException(ErrorCode.PLACE_NOT_FOUND);
        }

        PlaceLikeEntity placeLike = getPlaceLikeEntityBean.exec(deletePlaceLikeDto.getUserId(), deletePlaceLikeDto.getPlaceId());
        if (placeLike == null) {
            throw new CustomException(ErrorCode.PLACE_LIKE_NOT_FOUND);
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
