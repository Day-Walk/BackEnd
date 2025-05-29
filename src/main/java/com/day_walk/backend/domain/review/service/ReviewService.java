package com.day_walk.backend.domain.review.service;

import com.day_walk.backend.domain.review.bean.DeleteReviewEntityBean;
import com.day_walk.backend.domain.review.bean.GetReviewEntityBean;
import com.day_walk.backend.domain.review.bean.SaveReviewEntityBean;
import com.day_walk.backend.domain.review.data.ReviewEntity;
import com.day_walk.backend.domain.review.data.in.DeleteReviewDto;
import com.day_walk.backend.domain.review.data.in.SaveReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final SaveReviewEntityBean saveReviewEntityBean;
    private final GetReviewEntityBean getReviewEntityBean;
    private final DeleteReviewEntityBean deleteReviewEntityBean;

    public UUID saveReview(SaveReviewDto saveReviewDto) {
        ReviewEntity review = ReviewEntity.builder().saveReviewDto(saveReviewDto).build();

        saveReviewEntityBean.exec(review);

        ReviewEntity check = getReviewEntityBean.exec(review.getId());
        return check == null ? null : check.getId();
    }

    public boolean deleteReview(DeleteReviewDto deleteReviewDto) {
        ReviewEntity review = getReviewEntityBean.exec(deleteReviewDto.getReviewId());

        if (review == null) {
            return false;
        }

        deleteReviewEntityBean.exec(review);
        return true;
    }
}
