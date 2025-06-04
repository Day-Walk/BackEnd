package com.day_walk.backend.domain.tag.data.out;

import com.day_walk.backend.domain.place.data.PlaceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetTagByPlaceDto {
    private UUID placeId;
    private String placeName;
    private String categoryName;
    private String subCategoryName;
    private List<GetTagByReviewDto> tagList;

    @Builder
    public GetTagByPlaceDto(PlaceEntity place, String categoryName, String subCategoryName, List<GetTagByReviewDto> tagList) {
        this.placeId = place.getId();
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
        this.placeName = place.getName();
        this.tagList = tagList;
    }
}
