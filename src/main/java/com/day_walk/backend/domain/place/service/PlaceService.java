package com.day_walk.backend.domain.place.service;

import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.data.out.GetPlaceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final GetPlaceEntityBean getPlaceEntityBean;

    public GetPlaceDto getPlace(UUID placeId) {
        PlaceEntity place = getPlaceEntityBean.exec(placeId);
        // 카테고리, 서브카테고리 찾기 추가
        return GetPlaceDto.builder().place(place).build();
    }
}
