package com.day_walk.backend.domain.review.data.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SaveReviewDto {
    private UUID userId;
    private UUID placeId;
    private List<UUID> tagList;
    private double stars;
    private String imgUrl;
    private String content;
}
