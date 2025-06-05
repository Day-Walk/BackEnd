package com.day_walk.backend.domain.place.data.out;

import com.day_walk.backend.domain.place.data.PlaceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetPlaceWithStarDto {
    private UUID placeId;
    private String imgUrl;
    private String placeName;
    private String address;
    private String category;
    private String subCategory;
    private Map<String, BigDecimal> location;
    private double stars;

    @Builder
    public GetPlaceWithStarDto(PlaceEntity place, double stars,String category, String subCategory, String imgUrl) {
        this.placeId = place.getId();
        this.placeName = place.getName();
        this.address = place.getAddress();
        this.location = place.getLocation();
        this.stars = stars;
        this.category = category;
        this.subCategory = subCategory;
        this.imgUrl = imgUrl;
    }
}
