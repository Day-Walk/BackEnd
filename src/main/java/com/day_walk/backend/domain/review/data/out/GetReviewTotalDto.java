package com.day_walk.backend.domain.review.data.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetReviewTotalDto {
    private double stars;
    private int reviewNum;
    private List<String> tagList;
}
