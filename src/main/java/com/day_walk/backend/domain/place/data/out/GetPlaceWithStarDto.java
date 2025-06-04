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
    private List<String> imageUrlList;
    private String name;
    private String address;
    private String category;
    private String subCategory;
    private Map<String, BigDecimal> location;
    private double star;

    @Builder
    public GetPlaceWithStarDto(PlaceEntity place, String category, String subCategory, double star) {
        this.placeId = place.getId();
        this.imageUrlList = place.getImgList();
        this.name = place.getName();
        this.address = place.getAddress();
        this.location = place.getLocation();
        this.category = category;
        this.subCategory = subCategory;
    }
}
