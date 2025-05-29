package com.day_walk.backend.domain.place.data.out;

import com.day_walk.backend.domain.place.data.PlaceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetSearchPlaceDto {
    private UUID placeId;
    private String placeName;
    private String address;
    private String category;
    private String subCategory;
    private String imgUrl;
    private Map<String, BigDecimal> location;

    @Builder
    public GetSearchPlaceDto(PlaceEntity place) {
        this.placeId = place.getId();
        this.placeName = place.getName();
        this.address = place.getAddress();
        this.imgUrl = place.getImgList() == null ? null : place.getImgList().get(0);
        this.location = place.getLocation();
        this.category = place.getCategory();
        this.subCategory = place.getSubCategory();
    }
}
