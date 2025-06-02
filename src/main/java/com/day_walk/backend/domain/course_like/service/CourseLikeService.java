package com.day_walk.backend.domain.course_like.service;

import com.day_walk.backend.domain.course.bean.GetCourseEntityBean;
import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.course.data.dto.out.GetCourseByLikeDto;
import com.day_walk.backend.domain.course_like.bean.DeleteCourseLikeEntityBean;
import com.day_walk.backend.domain.course_like.bean.GetCourseLikeEntityBean;
import com.day_walk.backend.domain.course_like.bean.SaveCourseLikeEntityBean;
import com.day_walk.backend.domain.course_like.data.CourseLikeEntity;
import com.day_walk.backend.domain.course_like.data.in.DeleteCourseLikeDto;
import com.day_walk.backend.domain.course_like.data.in.SaveCourseLikeDto;
import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.out.GetPlaceByCourseDto;
import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CourseLikeService {
    private final GetUserEntityBean getUserEntityBean;
    private final GetCourseEntityBean getCourseEntityBean;
    private final SaveCourseLikeEntityBean saveCourseLikeEntityBean;
    private final DeleteCourseLikeEntityBean deleteCourseLikeEntityBean;
    private final GetCourseLikeEntityBean getCourseLikeEntityBean;
    private final GetPlaceEntityBean getPlaceEntityBean;

    public boolean saveCourseLike(SaveCourseLikeDto saveCourseLikeDto) {
        UserEntity user = getUserEntityBean.exec(saveCourseLikeDto.getUserId());
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        CourseEntity course = getCourseEntityBean.exec(saveCourseLikeDto.getCourseId());
        if (course == null) {
            throw new CustomException(ErrorCode.COURSE_NOT_FOUND);
        }

        CourseLikeEntity checkCourseLike = getCourseLikeEntityBean.exec(saveCourseLikeDto);
        if (checkCourseLike != null) {
            throw new CustomException(ErrorCode.COURSE_LIKE_IS_EXIST);
        }

        CourseLikeEntity courseLike = CourseLikeEntity.builder()
                .saveCourseLikeDto(saveCourseLikeDto)
                .build();

        saveCourseLikeEntityBean.exec(courseLike);

        return true;
    }

    public boolean deleteCourseLike(DeleteCourseLikeDto deleteCourseLikeDto) {
        CourseLikeEntity courseLike = getCourseLikeEntityBean.exec(deleteCourseLikeDto);
        if (courseLike == null) {
            throw new CustomException(ErrorCode.COURSE_LIKE_NOT_FOUND);
        }

        deleteCourseLikeEntityBean.exec(courseLike);
        return true;
    }

    public List<GetCourseByLikeDto> getCourseLike(UUID userId) {
        UserEntity user = getUserEntityBean.exec(userId);
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        List<CourseLikeEntity> courseLikeList = getCourseLikeEntityBean.exec(userId);
        if (courseLikeList == null) {
            return Collections.emptyList();
        }

        return courseLikeList.stream()
                .map(courseLike -> {
                    CourseEntity course = getCourseEntityBean.exec(courseLike.getCourseId());
                    if (!course.isVisible()) {
                        return null;
                    }

                    return GetCourseByLikeDto.builder()
                            .course(getCourseEntityBean.exec(courseLike.getCourseId()))
                            .user(getUserEntityBean.exec(courseLike.getUserId()))
                            .courseLike(getCourseLikeEntityBean.exec(course))
                            .placeList(course.getPlaceList().stream()
                                    .map(placeId -> GetPlaceByCourseDto.builder()
                                            .place(getPlaceEntityBean.exec(placeId))
                                            .build())
                                    .collect(Collectors.toList()))
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
