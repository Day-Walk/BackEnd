package com.day_walk.backend.domain.place.service;

import com.day_walk.backend.domain.category.bean.GetCategoryEntityBean;
import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.data.in.GetPlaceByMlDto;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    private final RestTemplate restTemplate;

    @Value("${ml-server-uri}")
    private String ML_SERVER_URI;

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
        String uri = UriComponentsBuilder
                .fromUriString(ML_SERVER_URI + "/recommend")
                .queryParam("userid", userId)
                .queryParam("query", searchStr)
                .toUriString();

        GetPlaceByMlDto response = restTemplate.getForEntity(uri, GetPlaceByMlDto.class).getBody();

        if (response == null) {
            throw new CustomException(ErrorCode.ML_SERVER_ERROR);
        }

        if (!response.isSuccess()) {
            return GetPlaceBySearchListDto.builder()
                    .recommendList(Collections.emptyList())
                    .placeList(Collections.emptyList())
                    .build();
        }

        return GetPlaceBySearchListDto.builder()
                .recommendList(response.getPlaces().getRecommend().stream()
                        .map(placeDto -> {
                            PlaceEntity place = getPlaceEntityBean.exec(placeDto.getId());
                            List<ReviewEntity> reviewList = getReviewEntityBean.exec(place);
                            double stars = getReviewStarsAvgBean.exec(reviewList);

                            return GetPlaceBySearchDto.builder()
                                    .place(place)
                                    .category(placeDto.getCategory())
                                    .subCategory(placeDto.getSubcategory())
                                    .stars(Math.max(0.0, Math.min(5.0, Math.round(stars * 10.0) / 10.0)))
                                    .build();
                        })
                        .collect(Collectors.toList()))
                .placeList(response.getPlaces().getNormal().stream()
                        .map(placeDto -> {
                            PlaceEntity place = getPlaceEntityBean.exec(placeDto.getId());
                            List<ReviewEntity> reviewList = getReviewEntityBean.exec(place);
                            double stars = getReviewStarsAvgBean.exec(reviewList);

                            return GetPlaceBySearchDto.builder()
                                    .place(place)
                                    .category(placeDto.getCategory())
                                    .subCategory(placeDto.getSubcategory())
                                    .stars(Math.max(0.0, Math.min(5.0, Math.round(stars * 10.0) / 10.0)))
                                    .build();
                        })
                        .collect(Collectors.toList()))
                .build();
    }
}
