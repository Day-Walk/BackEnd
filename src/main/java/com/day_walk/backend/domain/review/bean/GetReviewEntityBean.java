package com.day_walk.backend.domain.review.bean;

import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.review.data.ReviewEntity;
import com.day_walk.backend.domain.review.repository.ReviewRepository;
import com.day_walk.backend.domain.user.data.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GetReviewEntityBean {
    private final ReviewRepository reviewRepository;

    public ReviewEntity exec(UUID reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    public List<ReviewEntity> exec(PlaceEntity place) {
        return reviewRepository.findAllByPlaceIdAndHasDelete(place.getId(), false);
    }

    public List<ReviewEntity> exec(UserEntity user) {
        return reviewRepository.findAllByUserIdAndHasDelete(user.getId(), false);
    }
}
