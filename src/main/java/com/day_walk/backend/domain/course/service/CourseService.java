package com.day_walk.backend.domain.course.service;

import com.day_walk.backend.domain.category.bean.GetCategoryEntityBean;
import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.course.bean.*;
import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.course.data.dto.in.ChangeBooleanDto;
import com.day_walk.backend.domain.course.data.dto.in.ModifyCourseTitleDto;
import com.day_walk.backend.domain.course.data.dto.in.SaveCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetAllCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetSearchCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetUsersAllCourseDto;
import com.day_walk.backend.domain.course_like.bean.GetCourseLikeEntityBean;
import com.day_walk.backend.domain.course_like.data.CourseLikeEntity;
import com.day_walk.backend.domain.course_like.data.in.SaveCourseLikeDto;
import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.data.out.GetPlaceByCourseDto;
import com.day_walk.backend.domain.place.data.out.GetPlaceWithStarDto;
import com.day_walk.backend.domain.review.bean.GetReviewEntityBean;
import com.day_walk.backend.domain.review.bean.GetReviewStarsAvgBean;
import com.day_walk.backend.domain.review.data.ReviewEntity;
import com.day_walk.backend.domain.sub_category.bean.GetSubCategoryEntityBean;
import com.day_walk.backend.domain.sub_category.data.SubCategoryEntity;
import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final SaveCourseEntityBean saveCourseEntityBean;
    private final GetUserEntityBean getUserEntityBean;
    private final GetCourseEntityBean getCourseEntityBean;
    private final GetPlaceEntityBean getPlaceEntityBean;
    private final GetSubCategoryEntityBean getSubCategoryEntityBean;
    private final GetCategoryEntityBean getCategoryEntityBean;
    private final GetReviewEntityBean getReviewEntityBean;
    private final GetReviewStarsAvgBean getReviewStarsAvgBean;
    private final GetCourseLikeEntityBean getCourseLikeEntityBean;
    private final GetAllCourseEntityBean getAllCourseEntityBean;
    private final GetUsersAllCourseEntityBean getUsersAllCourseEntityBean;
    private final GetSearchCourseEntityBean getSearchCourseEntityBean;

    public UUID saveCourse(SaveCourseDto saveCourseDto) {
        UserEntity userEntity = getUserEntityBean.exec(saveCourseDto.getUserId());
        if (userEntity == null) return null;
        CourseEntity courseEntity = CourseEntity.builder()
                .id(UUID.randomUUID())
                .userId(saveCourseDto.getUserId())
                .title(saveCourseDto.getTitle())
                .visible(saveCourseDto.isVisible())
                .placeList(saveCourseDto.getPlaceList())
                .hasDelete(false)
                .build();
        saveCourseEntityBean.exec(courseEntity);
        CourseEntity course = saveCourseEntityBean.exec(courseEntity.getId());
        return course == null ? null : course.getId();
    }

    public UUID modifyCourseName(ModifyCourseTitleDto modifyCourseTitleDto) {
        CourseEntity courseEntity = getCourseEntityBean.exec(modifyCourseTitleDto.getCourseId());
        if (courseEntity == null) return null;
        courseEntity.modifyCourseTitle(modifyCourseTitleDto);
        saveCourseEntityBean.exec(courseEntity);
        CourseEntity courseId = saveCourseEntityBean.exec(courseEntity.getId());
        return courseId == null ? null : courseId.getId();
    }

    public UUID changeVisible(ChangeBooleanDto changeBooleanDto) {
        CourseEntity courseEntity = getCourseEntityBean.exec(changeBooleanDto.getCourseId());
        if (courseEntity == null) return null;
        courseEntity.changeVisible();
        saveCourseEntityBean.exec(courseEntity);
        CourseEntity courseId = saveCourseEntityBean.exec(courseEntity.getId());
        return courseId == null ? null : courseId.getId();
    }

    public UUID deleteCourse(ChangeBooleanDto changeBooleanDto) {
        CourseEntity courseEntity = getCourseEntityBean.exec(changeBooleanDto.getCourseId());
        if (courseEntity == null) return null;
        courseEntity.deleteCourse();
        saveCourseEntityBean.exec(courseEntity);
        CourseEntity courseId = saveCourseEntityBean.exec(courseEntity.getId());
        return courseId == null ? null : courseId.getId();
    }

    public GetCourseDto getCourse(UUID courseId) {
        CourseEntity courseEntity = getCourseEntityBean.exec(courseId);
        if ((courseEntity == null) || !courseEntity.isVisible() || courseEntity.isHasDelete()) return null;

        UserEntity userEntity = getUserEntityBean.exec(courseEntity.getUserId());

        List<GetPlaceWithStarDto> getPlaceDtoList = courseEntity.getPlaceList().stream()
                .map(placeId -> {
                    PlaceEntity placeEntity = getPlaceEntityBean.exec(placeId);
                    SubCategoryEntity subCategory = getSubCategoryEntityBean.exec(placeEntity.getSubCategoryId());
                    CategoryEntity category = getCategoryEntityBean.exec(subCategory.getCategoryId());

                    List<ReviewEntity> reviewList = getReviewEntityBean.exec(placeEntity);
                    double stars = getReviewStarsAvgBean.exec(reviewList);

                    return GetPlaceWithStarDto.builder()
                            .place(placeEntity)
                            .category(category.getName())
                            .subCategory(subCategory.getName())
                            .star(stars)
                            .build();
                })
                .collect(Collectors.toList());

        boolean like = false;
        CourseLikeEntity likeEntity = getCourseLikeEntityBean.exec(new SaveCourseLikeDto(userEntity.getId(), courseEntity.getId()));
        like = (likeEntity != null);

        return GetCourseDto.builder()
                .title(courseEntity.getTitle())
                .placeList(getPlaceDtoList)
                .like(like)
                .courseLike(getCourseLikeEntityBean.exec(courseEntity))
                .build();
    }

    public List<GetAllCourseDto> getAllCourse(String sortStr, UUID userId) {
        List<CourseEntity> courseEntityList = getAllCourseEntityBean.exec(sortStr);
        if (courseEntityList == null) return Collections.emptyList();

        UserEntity userEntity = getUserEntityBean.exec(userId);
        if (userEntity == null) return null;

        return courseEntityList.stream()
                .filter(courseEntity -> courseEntity.isVisible() && !courseEntity.isHasDelete())
                .map(courseEntity -> {
                    boolean liked = getCourseLikeEntityBean.exec(
                            new SaveCourseLikeDto(userId, courseEntity.getId())
                    ) != null;

                    List<GetPlaceByCourseDto> placeList = courseEntity.getPlaceList().stream()
                            .map(placeId -> {
                                PlaceEntity placeEntity = getPlaceEntityBean.exec(placeId);
                                return GetPlaceByCourseDto.builder()
                                        .place(placeEntity)
                                        .build();
                            })
                            .collect(Collectors.toList());

                    return GetAllCourseDto.builder()
                            .courseId(courseEntity.getId())
                            .title(courseEntity.getTitle())
                            .userName(getUserEntityBean.exec(courseEntity.getUserId()).getName())
                            .placeList(placeList)
                            .like(liked)
                            .courseLike(getCourseLikeEntityBean.exec(courseEntity))
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<GetUsersAllCourseDto> getUsersAllCourse(UUID userId) {
        List<CourseEntity> courseEntityList = getUsersAllCourseEntityBean.exec(userId);
        if (courseEntityList == null) return Collections.emptyList();

        return courseEntityList.stream()
                .filter(courseEntity -> courseEntity.isVisible() && !courseEntity.isHasDelete())
                .map(courseEntity -> {

                    List<GetPlaceByCourseDto> placeList = courseEntity.getPlaceList().stream()
                            .map(placeId -> {
                                PlaceEntity placeEntity = getPlaceEntityBean.exec(placeId);
                                return GetPlaceByCourseDto.builder()
                                        .place(placeEntity)
                                        .build();
                            })
                            .collect(Collectors.toList());

                    return GetUsersAllCourseDto.builder()
                            .courseId(courseEntity.getId())
                            .title(courseEntity.getTitle())
                            .visible(courseEntity.isVisible())
                            .placeList(placeList)
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<GetSearchCourseDto> getSearchCourse(String searchStr, String sortStr, UUID userId) {
        List<CourseEntity> courseEntityList = getSearchCourseEntityBean.exec(searchStr, sortStr);
        if (courseEntityList == null) return Collections.emptyList();

        UserEntity userEntity = getUserEntityBean.exec(userId);
        if (userEntity == null) return null;

        return courseEntityList.stream()
                .filter(courseEntity -> courseEntity.isVisible() && !courseEntity.isHasDelete())
                .map(courseEntity -> {
                    boolean liked = getCourseLikeEntityBean.exec(
                            new SaveCourseLikeDto(userId, courseEntity.getId())
                    ) != null;

                    List<GetPlaceByCourseDto> placeList = courseEntity.getPlaceList().stream()
                            .map(placeId -> {
                                PlaceEntity placeEntity = getPlaceEntityBean.exec(placeId);
                                return GetPlaceByCourseDto.builder()
                                        .place(placeEntity)
                                        .build();
                            })
                            .collect(Collectors.toList());

                    return GetSearchCourseDto.builder()
                            .courseId(courseEntity.getId())
                            .title(courseEntity.getTitle())
                            .userName(getUserEntityBean.exec(courseEntity.getUserId()).getName())
                            .placeList(placeList)
                            .like(liked)
                            .courseLike(getCourseLikeEntityBean.exec(courseEntity))
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
