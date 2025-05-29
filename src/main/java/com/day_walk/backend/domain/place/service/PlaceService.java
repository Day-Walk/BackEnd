package com.day_walk.backend.domain.place.service;

import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.data.out.GetSearchPlaceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final GetPlaceEntityBean getPlaceEntityBean;

    public List<GetSearchPlaceDto> searchPlace(String searchStr) {
        List<PlaceEntity> placeList = getPlaceEntityBean.exec(searchStr);

        return placeList.stream()
                .map(place -> GetSearchPlaceDto.builder()
                        .place(place)
                        .build())
                .collect(Collectors.toList());

    }
}
