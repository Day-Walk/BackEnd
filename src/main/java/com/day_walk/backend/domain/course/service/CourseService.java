package com.day_walk.backend.domain.course.service;

import com.day_walk.backend.domain.category.bean.GetCategoryEntityBean;
//import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.course.bean.GetCourseEntityBean;
import com.day_walk.backend.domain.course.bean.SaveCourseEntityBean;
import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.course.data.dto.in.SaveCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetCourseDto;
import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
//import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.data.out.GetPlaceDto;
import com.day_walk.backend.domain.sub_category.bean.GetSubCategoryEntityBean;
//import com.day_walk.backend.domain.sub_category.data.SubCategoryEntity;
import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final SaveCourseEntityBean saveCourseEntityBean;
    private final GetUserEntityBean getUserEntityBean;

    public UUID saveCourse(SaveCourseDto saveCourseDto) {
        UserEntity userEntity = getUserEntityBean.exec(saveCourseDto.getUserId());
        if (userEntity == null) return null;
        CourseEntity courseEntity = CourseEntity.builder()
                .id(UUID.randomUUID())
                .userId(saveCourseDto.getUserId())
                .title(saveCourseDto.getTitle())
                .visible(saveCourseDto.isVisible())
                .placeList(saveCourseDto.getPlaceList())
                .build();
        saveCourseEntityBean.exec(courseEntity);
        CourseEntity course = saveCourseEntityBean.exec(courseEntity.getId());
        return course == null ? null : course.getId();
    }

    private final GetCourseEntityBean getCourseEntityBean;
    private final GetPlaceEntityBean getPlaceEntityBean;
    private final GetSubCategoryEntityBean getSubCategoryEntityBean;
    private final GetCategoryEntityBean getCategoryEntityBean;

    public GetCourseDto getCourse(UUID courseId) {
        CourseEntity courseEntity = getCourseEntityBean.exec(courseId);
        if((courseEntity == null)&& !courseEntity.isVisible() && !courseEntity.isHasDelete()) return null;
        UserEntity userEntity = getUserEntityBean.exec(courseEntity.getUserId());
        List<GetPlaceDto> getPlaceDtoList = new ArrayList<>();
//        for (UUID placeId : courseEntity.getPlaceList()) {
//            PlaceEntity placeEntity = getPlaceEntityBean.exec(placeId);
//            SubCategoryEntity subCategory = getSubCategoryEntityBean.exec(placeEntity.getSubCategoryId());
//            CategoryEntity category = getCategoryEntityBean.exec(subCategory.getCategoryId());
//            GetPlaceDto getPlaceDto = GetPlaceDto.builder()
//                    .place(placeEntity)
//                    .category(category.getName())
//                    .subCategory(subCategory.getName())
//                    .build();
//            getPlaceDtoList.add(getPlaceDto);
//        }

        GetCourseDto courseInfo = GetCourseDto.builder()
                .userName(userEntity.getName())
                .title(courseEntity.getTitle())
//                .placeList(getPlaceDtoList)
                .build();

        return courseInfo == null ? null : courseInfo;
    }
}
