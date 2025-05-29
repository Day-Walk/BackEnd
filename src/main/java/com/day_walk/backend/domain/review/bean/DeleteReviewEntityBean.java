package com.day_walk.backend.domain.review.bean;

import com.day_walk.backend.domain.review.data.ReviewEntity;
import com.day_walk.backend.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteReviewEntityBean {
    private final ReviewRepository reviewRepository;

    public void exec(ReviewEntity review) {
        reviewRepository.delete(review);
    }
}
