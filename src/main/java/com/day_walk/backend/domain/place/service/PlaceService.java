package com.day_walk.backend.domain.place.service;

import com.day_walk.backend.domain.category.bean.GetCategoryEntityBean;
import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.data.out.GetPlaceDto;
import com.day_walk.backend.domain.place.data.out.GetSearchPlaceDto;
import com.day_walk.backend.domain.sub_category.bean.GetSubCategoryEntityBean;
import com.day_walk.backend.domain.sub_category.data.SubCategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final GetPlaceEntityBean getPlaceEntityBean;
    private final GetSubCategoryEntityBean getSubCategoryEntityBean;
    private final GetCategoryEntityBean getCategoryEntityBean;

    public List<GetSearchPlaceDto> searchPlace(String searchStr) {
        List<PlaceEntity> placeList = getPlaceEntityBean.exec(searchStr);

        return placeList.stream()
                .map(place -> GetSearchPlaceDto.builder()
                        .place(place)
                        .build())
                .collect(Collectors.toList());

    }
}
