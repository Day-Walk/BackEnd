package com.day_walk.backend.domain.review.bean;

import com.day_walk.backend.domain.review.data.ReviewEntity;
import com.day_walk.backend.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GetReviewEntityBean {
    private final ReviewRepository reviewRepository;

    public ReviewEntity exec(UUID reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }
}
