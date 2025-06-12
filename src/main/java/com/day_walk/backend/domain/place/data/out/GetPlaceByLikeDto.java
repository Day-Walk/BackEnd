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
public class GetPlaceByLikeDto {
    private UUID placeId;
    private String name;
    private String address;
    private String imgUrl;
    private double stars;

    @Builder
    public GetPlaceByLikeDto(PlaceEntity place, double stars) {
        this.placeId = place.getId();
        this.name = place.getName();
        this.address = place.getAddress();
        this.imgUrl = place.getImgList().isEmpty() ? null : place.getImgList().get(0);
        this.stars = stars;
    }
}
