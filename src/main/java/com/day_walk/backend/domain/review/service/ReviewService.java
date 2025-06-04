package com.day_walk.backend.domain.review.service;

import com.day_walk.backend.domain.category.bean.GetCategoryEntityBean;
import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.review.bean.DeleteReviewEntityBean;
import com.day_walk.backend.domain.review.bean.GetReviewEntityBean;
import com.day_walk.backend.domain.review.bean.SaveReviewEntityBean;
import com.day_walk.backend.domain.review.data.ReviewEntity;
import com.day_walk.backend.domain.review.data.in.DeleteReviewDto;
import com.day_walk.backend.domain.review.data.in.SaveReviewDto;
import com.day_walk.backend.domain.review.data.out.GetReviewByPlaceDto;
import com.day_walk.backend.domain.review.data.out.GetReviewByUserDto;
import com.day_walk.backend.domain.review.data.out.GetReviewTotalDto;
import com.day_walk.backend.domain.sub_category.bean.GetSubCategoryEntityBean;
import com.day_walk.backend.domain.sub_category.data.SubCategoryEntity;
import com.day_walk.backend.domain.tag.bean.GetTagEntityBean;
import com.day_walk.backend.domain.tag.data.TagEntity;
import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final SaveReviewEntityBean saveReviewEntityBean;
    private final GetReviewEntityBean getReviewEntityBean;
    private final DeleteReviewEntityBean deleteReviewEntityBean;
    private final GetPlaceEntityBean getPlaceEntityBean;
    private final GetUserEntityBean getUserEntityBean;
    private final GetTagEntityBean getTagEntityBean;
    private final GetCategoryEntityBean getCategoryEntityBean;
    private final GetSubCategoryEntityBean getSubCategoryEntityBean;

    public UUID saveReview(SaveReviewDto saveReviewDto) {
        UserEntity user = getUserEntityBean.exec(saveReviewDto.getUserId());
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        PlaceEntity place = getPlaceEntityBean.exec(saveReviewDto.getPlaceId());
        if (place == null) {
            throw new CustomException(ErrorCode.PLACE_NOT_FOUND);
        }

        List<TagEntity> tagList = getTagEntityBean.exec(saveReviewDto.getTagList());
        if (tagList.size() != saveReviewDto.getTagList().size()) {
            throw new CustomException(ErrorCode.TAG_NOT_FOUND);
        }

        ReviewEntity review = ReviewEntity.builder()
                .saveReviewDto(saveReviewDto)
                .build();

        saveReviewEntityBean.exec(review);

        return review.getId();
    }

    public boolean deleteReview(DeleteReviewDto deleteReviewDto) {
        ReviewEntity review = getReviewEntityBean.exec(deleteReviewDto.getReviewId());

        if (review == null || review.isHasDelete()) {
            throw new CustomException(ErrorCode.REVIEW_NOT_FOUND);
        }

        ReviewEntity deleteReview = deleteReviewEntityBean.exec(review);

        saveReviewEntityBean.exec(deleteReview);

        return true;
    }

    public List<GetReviewByUserDto> getReviewByUser(UUID userId) {
        UserEntity user = getUserEntityBean.exec(userId);
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        List<ReviewEntity> reviewList = getReviewEntityBean.exec(user);

        return reviewList.stream()
                .map(review -> {
                    PlaceEntity place = getPlaceEntityBean.exec(review.getPlaceId());
                    SubCategoryEntity subCategory = getSubCategoryEntityBean.exec(place.getSubCategoryId());
                    CategoryEntity category = getCategoryEntityBean.exec(subCategory.getCategoryId());
                    List<TagEntity> tagList = getTagEntityBean.exec(review.getTagList());

                    return GetReviewByUserDto.builder()
                            .place(place)
                            .categoryName(category.getName())
                            .subCategoryName(subCategory.getName())
                            .review(review)
                            .tagList(tagList.isEmpty()
                                    ? Collections.emptyList()
                                    : tagList.stream()
                                    .map(TagEntity::getFullName)
                                    .toList())
                            .build();
                }).collect(Collectors.toList());
    }

    public List<GetReviewByPlaceDto> getReviewByPlace(UUID placeId) {
        PlaceEntity place = getPlaceEntityBean.exec(placeId);
        if (place == null) {
            throw new CustomException(ErrorCode.PLACE_NOT_FOUND);
        }

        List<ReviewEntity> reviewList = getReviewEntityBean.exec(place);

        return reviewList.stream()
                .map(review -> {
                    List<TagEntity> tagList = getTagEntityBean.exec(review.getTagList());

                    return GetReviewByPlaceDto.builder()
                            .review(review)
                            .user(getUserEntityBean.exec(review.getUserId()))
                            .tagList(tagList.isEmpty()
                                    ? Collections.emptyList()
                                    : tagList.stream()
                                    .map(TagEntity::getFullName)
                                    .toList())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public GetReviewTotalDto getReviewTotal(UUID placeId) {
        PlaceEntity place = getPlaceEntityBean.exec(placeId);
        if (place == null) {
            throw new CustomException(ErrorCode.PLACE_NOT_FOUND);
        }

        List<ReviewEntity> reviewList = getReviewEntityBean.exec(place);

        return GetReviewTotalDto.builder()
                .reviewNum(reviewList.size())
                .stars(reviewList.stream()
                        .mapToDouble(ReviewEntity::getStars)
                        .average()
                        .orElse(0.0))
                .tagList(
                        reviewList.stream()
                                .flatMap(review -> review.getTagList().stream())
                                .collect(Collectors.groupingBy(tagId -> tagId, Collectors.counting()))
                                .entrySet().stream()
                                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                                .limit(5)
                                .map(entry -> getTagEntityBean.exec(entry.getKey()).getFullName())
                                .collect(Collectors.toList())
                ).build();
    }
}
