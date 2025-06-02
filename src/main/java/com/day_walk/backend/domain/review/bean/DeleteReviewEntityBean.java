package com.day_walk.backend.domain.review.bean;

import com.day_walk.backend.domain.review.data.ReviewEntity;
import org.springframework.stereotype.Component;

@Component
public class DeleteReviewEntityBean {
    public ReviewEntity exec(ReviewEntity review) {
        review.delete();
        System.out.println(review.isHasDelete());
        return review;
    }
}
