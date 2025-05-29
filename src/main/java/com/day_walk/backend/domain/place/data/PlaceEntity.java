package com.day_walk.backend.domain.place.data;

import com.day_walk.backend.global.BaseEntity;
import com.day_walk.backend.global.util.StringToListConverter;
import com.day_walk.backend.global.util.StringToMapConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "place")
public class PlaceEntity extends BaseEntity {
    @Id
    private UUID id;
    private String name;
    private String content;
    private String address;
    private String phoneNum;
    private String openTime;
    private String closeDate;

    @Convert(converter = StringToMapConverter.class)
    private Map<String, BigDecimal> location;

    @Convert(converter = StringToListConverter.class)
    private List<String> imgList;

    private UUID subCategoryId;

    @Builder
    public PlaceEntity(String name, String content, String address, String phoneNum, String openTime, String closeDate, Map<String, BigDecimal> location, List<String> imgList) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.content = content;
        this.address = address;
        this.phoneNum = phoneNum;
        this.openTime = openTime;
        this.closeDate = closeDate;
        this.location = location;
        this.imgList = imgList;
        this.subCategoryId = UUID.randomUUID();
    }
}
