package com.day_walk.backend.domain.review.data.out;

import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.review.data.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetReviewByUserDto {
    private String placeName;
    private String address;
    private String categoryName;
    private String subCategoryName;
    private String content;
    private Double stars;
    private String imgUrl;
    private LocalDateTime createAt;
    private List<String> tagList;

    @Builder
    public GetReviewByUserDto(PlaceEntity place, String categoryName, String subCategoryName, ReviewEntity review, List<String> tagList) {
        this.placeName = place.getName();
        this.address = place.getAddress();
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
        this.content = review.getContent();
        this.stars = review.getStars();
        this.imgUrl = review.getImgUrl();
        this.createAt = review.getCreateAt();
        this.tagList = tagList;
    }
}
