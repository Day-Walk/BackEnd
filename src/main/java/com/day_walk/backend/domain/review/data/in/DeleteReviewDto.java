package com.day_walk.backend.domain.review.data.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeleteReviewDto {
    private UUID reviewId;
}
