package com.day_walk.backend.domain.place.service;

import com.day_walk.backend.domain.category.bean.GetCategoryEntityBean;
import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.data.out.GetPlaceDto;
import com.day_walk.backend.domain.sub_category.bean.GetSubCategoryEntityBean;
import com.day_walk.backend.domain.sub_category.data.SubCategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final GetPlaceEntityBean getPlaceEntityBean;
    private final GetSubCategoryEntityBean getSubCategoryEntityBean;
    private final GetCategoryEntityBean getCategoryEntityBean;

    public GetPlaceDto getPlace(UUID placeId) {
        PlaceEntity place = getPlaceEntityBean.exec(placeId);
        SubCategoryEntity subCategory = getSubCategoryEntityBean.exec(place.getSubCategoryId());
        CategoryEntity category = getCategoryEntityBean.exec(subCategory.getCategoryId());

        return GetPlaceDto.builder()
                .place(place)
                .category(category.getName())
                .subCategory(subCategory.getName())
                .build();
    }
}
