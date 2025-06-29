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
public class GetPlaceByChatDto {
    private UUID placeId;
    private String name;
    private String address;
    private String imgUrl;
    private Map<String, BigDecimal> location;

    @Builder
    public GetPlaceByChatDto(PlaceEntity place) {
        this.placeId = place.getId();
        this.name = place.getName();
        this.address = place.getAddress();
        this.imgUrl = place.getImgList().isEmpty() ? "" : place.getImgList().get(0);
        this.location = place.getLocation();
    }
}
