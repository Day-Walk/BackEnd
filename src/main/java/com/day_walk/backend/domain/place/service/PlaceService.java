package com.day_walk.backend.domain.place.service;

import com.day_walk.backend.domain.category.bean.GetCategoryEntityBean;
import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.data.out.GetPlaceBySearchDto;
import com.day_walk.backend.domain.place.data.out.GetPlaceBySearchListDto;
import com.day_walk.backend.domain.place.data.out.GetPlaceDto;
import com.day_walk.backend.domain.place_like.bean.GetPlaceLikeEntityBean;
import com.day_walk.backend.domain.place_like.data.PlaceLikeEntity;
import com.day_walk.backend.domain.review.bean.GetReviewEntityBean;
import com.day_walk.backend.domain.review.bean.GetReviewStarsAvgBean;
import com.day_walk.backend.domain.review.data.ReviewEntity;
import com.day_walk.backend.domain.sub_category.bean.GetSubCategoryEntityBean;
import com.day_walk.backend.domain.sub_category.data.SubCategoryEntity;
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
public class PlaceService {
    private final GetPlaceEntityBean getPlaceEntityBean;
    private final GetUserEntityBean getUserEntityBean;
    private final GetSubCategoryEntityBean getSubCategoryEntityBean;
    private final GetCategoryEntityBean getCategoryEntityBean;
    private final GetPlaceLikeEntityBean getPlaceLikeEntityBean;
    private final GetReviewEntityBean getReviewEntityBean;
    private final GetReviewStarsAvgBean getReviewStarsAvgBean;

    public GetPlaceDto getPlace(UUID placeId, UUID userId) {
        PlaceEntity place = getPlaceEntityBean.exec(placeId);
        if (place == null) {
            throw new CustomException(ErrorCode.PLACE_NOT_FOUND);
        }
        SubCategoryEntity subCategory = getSubCategoryEntityBean.exec(place.getSubCategoryId());
        CategoryEntity category = getCategoryEntityBean.exec(subCategory.getCategoryId());

        UserEntity user = getUserEntityBean.exec(userId);
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        PlaceLikeEntity placeLike = getPlaceLikeEntityBean.exec(userId, placeId);

        return GetPlaceDto.builder()
                .place(place)
                .category(category.getName())
                .subCategory(subCategory.getName())
                .like(placeLike != null)
                .build();
    }

    public GetPlaceBySearchListDto searchPlace(String searchStr, UUID userId) {
        // 검색엔진 사용 로직 추가

        // 테스트 로직 추후 삭제 예정
        List<PlaceEntity> placeEntityList = getPlaceEntityBean.exec(searchStr);
        List<PlaceEntity> recommendList = new ArrayList<>();
        List<PlaceEntity> placeList = new ArrayList<>();

        if (!placeEntityList.isEmpty() && placeEntityList.size() < 12) {
            recommendList.add(placeEntityList.get(0));
            for (int i = 1; i < placeEntityList.size(); i++) {
                placeList.add(placeEntityList.get(i));
            }
        } else if (!placeEntityList.isEmpty() && placeEntityList.size() < 23) {
            recommendList.add(placeEntityList.get(0));
            recommendList.add(placeEntityList.get(1));
            for (int i = 2; i < placeEntityList.size(); i++) {
                placeList.add(placeEntityList.get(i));
            }
        } else if(!placeEntityList.isEmpty()) {
            recommendList.add(placeEntityList.get(0));
            recommendList.add(placeEntityList.get(1));
            recommendList.add(placeEntityList.get(2));
            for (int i = 3; i < 24; i++) {
                placeList.add(placeEntityList.get(i));
            }
        }

        return GetPlaceBySearchListDto.builder()
                .recommendList(recommendList.stream()
                        .map(place -> {
                            SubCategoryEntity subCategory = getSubCategoryEntityBean.exec(place.getSubCategoryId());
                            CategoryEntity category = getCategoryEntityBean.exec(subCategory.getCategoryId());
                            List<ReviewEntity> reviewList = getReviewEntityBean.exec(place);
                            double stars = getReviewStarsAvgBean.exec(reviewList);

                            return GetPlaceBySearchDto.builder()
                                    .place(place)
                                    .category(category.getName())
                                    .subCategory(subCategory.getName())
                                    .stars(Math.max(0.0, Math.min(5.0, Math.round(stars * 10.0) / 10.0)))
                                    .build();
                        })
                        .collect(Collectors.toList()))
                .placeList(placeList.stream()
                        .map(place -> {
                            SubCategoryEntity subCategory = getSubCategoryEntityBean.exec(place.getSubCategoryId());
                            CategoryEntity category = getCategoryEntityBean.exec(subCategory.getCategoryId());
                            List<ReviewEntity> reviewList = getReviewEntityBean.exec(place);
                            double stars = getReviewStarsAvgBean.exec(reviewList);

                            return GetPlaceBySearchDto.builder()
                                    .place(place)
                                    .category(category.getName())
                                    .subCategory(subCategory.getName())
                                    .stars(Math.max(0.0, Math.min(5.0, Math.round(stars * 10.0) / 10.0)))
                                    .build();
                        })
                        .collect(Collectors.toList()))
                .build();
    }
}
