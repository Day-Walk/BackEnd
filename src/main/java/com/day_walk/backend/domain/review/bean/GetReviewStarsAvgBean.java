package com.day_walk.backend.domain.review.bean;

import com.day_walk.backend.domain.review.data.ReviewEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetReviewStarsAvgBean {
    public double exec(List<ReviewEntity> reviewList) {
        return reviewList.stream()
                .mapToDouble(ReviewEntity::getStars)
                .average()
                .orElse(0.0);
    }
}
