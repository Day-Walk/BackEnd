package com.day_walk.backend.domain.tag.service;

import com.day_walk.backend.domain.category.bean.GetCategoryEntityBean;
import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.sub_category.bean.GetSubCategoryEntityBean;
import com.day_walk.backend.domain.sub_category.data.SubCategoryEntity;
import com.day_walk.backend.domain.tag.bean.GetTagEntityBean;
import com.day_walk.backend.domain.tag.data.TagEntity;
import com.day_walk.backend.domain.tag.data.out.GetTagByCategoryDto;
import com.day_walk.backend.domain.tag.data.out.GetTagByPlaceDto;
import com.day_walk.backend.domain.tag.data.out.GetTagByReviewDto;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagService {
    private final GetTagEntityBean getTagEntityBean;
    private final GetCategoryEntityBean getCategoryEntityBean;
    private final GetPlaceEntityBean getPlaceEntityBean;
    private final GetSubCategoryEntityBean getSubCategoryEntityBean;

    public List<GetTagByCategoryDto> getTagByCategory(UUID categoryId) {
        CategoryEntity category = getCategoryEntityBean.exec(categoryId);
        if (category == null) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        List<TagEntity> tagEntityList = getTagEntityBean.exec(category);

        return tagEntityList.stream()
                .map(tag -> GetTagByCategoryDto.builder()
                        .tag(tag)
                        .build())
                .collect(Collectors.toList());
    }

    public GetTagByPlaceDto getTagByPlace(UUID placeId) {
        PlaceEntity place = getPlaceEntityBean.exec(placeId);
        if (place == null) {
            throw new CustomException(ErrorCode.PLACE_NOT_FOUND);
        }

        SubCategoryEntity subCategory = getSubCategoryEntityBean.exec(place);
        CategoryEntity category = getCategoryEntityBean.exec(subCategory.getCategoryId());

        List<GetTagByReviewDto> tagList = getTagEntityBean.exec(category).stream()
                .map(tag -> GetTagByReviewDto.builder()
                        .tag(tag)
                        .build())
                .collect(Collectors.toList());

        return GetTagByPlaceDto.builder()
                .place(place)
                .categoryName(category.getName())
                .subCategoryName(subCategory.getName())
                .tagList(tagList)
                .build();
    }
}
