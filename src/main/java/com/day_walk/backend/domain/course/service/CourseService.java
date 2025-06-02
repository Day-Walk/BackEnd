package com.day_walk.backend.domain.course.service;

import com.day_walk.backend.domain.category.bean.GetCategoryEntityBean;
import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.course.bean.GetAllCourseEntityBean;
import com.day_walk.backend.domain.course.bean.GetCourseEntityBean;
import com.day_walk.backend.domain.course.bean.GetUsersAllCourseEntityBean;
import com.day_walk.backend.domain.course.bean.SaveCourseEntityBean;
import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.course.data.dto.in.ChangeBooleanDto;
import com.day_walk.backend.domain.course.data.dto.in.ModifyCourseTitleDto;
import com.day_walk.backend.domain.course.data.dto.in.SaveCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetAllCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetUsersAllCourseDto;
import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.data.out.GetPlaceDto;
import com.day_walk.backend.domain.sub_category.bean.GetSubCategoryEntityBean;
import com.day_walk.backend.domain.sub_category.data.SubCategoryEntity;
import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.domain.user.data.dto.out.GetUserDto;
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
        List<GetPlaceDto> getPlaceDtoList = new ArrayList<>();
        for (UUID placeId : courseEntity.getPlaceList()) {
            PlaceEntity placeEntity = getPlaceEntityBean.exec(placeId);
            SubCategoryEntity subCategory = getSubCategoryEntityBean.exec(placeEntity.getSubCategoryId());
            CategoryEntity category = getCategoryEntityBean.exec(subCategory.getCategoryId());
            // 리뷰의 평균 값 추가 예정
            // 코스 좋아요 여부 추가 예정
            // 코스 좋아요 전체 갯수 추가 예정
            GetPlaceDto getPlaceDto = GetPlaceDto.builder()
                    .place(placeEntity)
                    .category(category.getName())
                    .subCategory(subCategory.getName())
                    .build();
            getPlaceDtoList.add(getPlaceDto);
        }

        return GetCourseDto.builder()
                .userName(userEntity.getName())
                .title(courseEntity.getTitle())
                .placeList(getPlaceDtoList)
                .build();
    }

    private final GetAllCourseEntityBean getAllCourseEnitityBean;
    public List<GetAllCourseDto> getAllCourse(String sortStr) {
        List<CourseEntity> courseEntityList = getAllCourseEnitityBean.exec(sortStr);
        // 코스 좋아요 갯수 추가 예정
        // 코스 좋아요 전체 갯수 추가 예정
        // placeList 추가 예정

        if(courseEntityList == null) return Collections.emptyList();

        return courseEntityList.stream()
                .map(courseEntity -> {
                    if (!courseEntity.isVisible() || courseEntity.isHasDelete()) {
                        return null;
                    }

                    return GetAllCourseDto.builder()
                            .courseId(courseEntity.getId())
                            .title(courseEntity.getTitle())
                            .userName(getUserEntityBean.exec(courseEntity.getUserId()).getName())
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    private final GetUsersAllCourseEntityBean getUsersAllCourseEntityBean;

    public List<GetUsersAllCourseDto> getUsersAllCourse(UUID userId) {
        List<CourseEntity> courseEntityList = getUsersAllCourseEntityBean.exec(userId);
        if(courseEntityList == null ) return Collections.emptyList();

        return courseEntityList.stream()
                .map(courseEntity -> {
                    if (courseEntity.isHasDelete()) {
                        return null;
                    }

                    // courseLike 총 갯수 추가 예정
                    // placeInfo 추가 예정 (GetPlaceByCourseDto)
                    return GetUsersAllCourseDto.builder()
                            .courseId(courseEntity.getId())
                            .title(courseEntity.getTitle())
                            .visible(courseEntity.isVisible())
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
