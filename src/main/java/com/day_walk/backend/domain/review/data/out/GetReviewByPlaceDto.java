package com.day_walk.backend.domain.review.data.out;

import com.day_walk.backend.domain.review.data.ReviewEntity;
import com.day_walk.backend.domain.user.data.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetReviewByPlaceDto {
    private String userName;
    private String content;
    private String imgUrl;
    private double stars;
    private List<String> tagList;
    private LocalDateTime createAt;

    @Builder
    public GetReviewByPlaceDto(ReviewEntity review, UserEntity user, List<String> tagList) {
        this.userName = user.getName();
        this.content = review.getContent();
        this.imgUrl = review.getImgUrl();
        this.stars = review.getStars();
        this.tagList = tagList;
        this.createAt = review.getCreateAt();
    }
}
