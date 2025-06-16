package com.day_walk.backend.domain.place.data.out;

import com.day_walk.backend.domain.place.data.PlaceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetPlaceDto {
    private UUID placeId;
    private List<String> imgUrlList;
    private String name;
    private String address;
    private String category;
    private String subCategory;
    private Map<String, BigDecimal> location;
    private String openTime;
    private String closeDate;
    private String content;
    private String phoneNum;
    private boolean like;

    @Builder
    public GetPlaceDto(PlaceEntity place, String category, String subCategory, boolean like) {
        this.placeId = place.getId();
        this.imgUrlList = place.getImgList();
        this.name = place.getName();
        this.address = place.getAddress();
        this.location = place.getLocation();
        this.category = category;
        this.subCategory = subCategory;
        this.openTime = place.getOpenTime();
        this.closeDate = place.getCloseDate();
        this.content = place.getContent();
        this.phoneNum = place.getPhoneNum();
        this.like = like;
    }
}
