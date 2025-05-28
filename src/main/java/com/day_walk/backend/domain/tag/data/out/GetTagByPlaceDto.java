package com.day_walk.backend.domain.tag.data.out;

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
    private List<GetTagByCategoryDto> tagList;

    /*
    @Builder
    public GetTagByPlaceDto(PlaceEntity place, List<GetTagByCategoryDto> tagList) {
        this.placeId = place.getId();
        this.placeName = place.getName();
        this.tagList = tagList;
    }
    */
}
