package com.day_walk.backend.domain.place.data.in;

import com.day_walk.backend.global.util.StringToListConverter;
import com.day_walk.backend.global.util.StringToMapConverter;
import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SavePlaceDto {
    private String name;
    private String content;
    private String address;
    private String phoneNum;
    private String openTime;
    private String closeDate;
    private String category;
    private String subCategory;
    private Map<String, BigDecimal> location;
    private List<String> imgList;
}
