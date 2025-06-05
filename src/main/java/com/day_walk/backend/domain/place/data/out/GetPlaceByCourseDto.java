package com.day_walk.backend.domain.place.data.out;

import com.day_walk.backend.domain.place.data.PlaceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetPlaceByCourseDto {
    private UUID placeId;
    private String placeName;
    private String imgUrl;
    private String address;

    @Builder
    public GetPlaceByCourseDto(PlaceEntity place) {
        this.placeId = place.getId();
        this.placeName = place.getName();
        this.imgUrl = place.getImgList().isEmpty() ? null : place.getImgList().get(0);
        this.address = place.getAddress();
    }
}
