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
public class GetPlaceBySearchDto {
    private UUID placeId;
    private String imgUrl;
    private String name;
    private String address;
    private String category;
    private String subCategory;
    private Map<String, BigDecimal> location;

    @Builder
    public GetPlaceBySearchDto(PlaceEntity place, String category, String subCategory) {
        this.placeId = place.getId();
        this.imgUrl = place.getImgList().isEmpty() ? null : place.getImgList().get(0);
        this.name = place.getName();
        this.address = place.getAddress();
        this.category = category;
        this.subCategory = subCategory;
        this.location = place.getLocation();
    }
}
