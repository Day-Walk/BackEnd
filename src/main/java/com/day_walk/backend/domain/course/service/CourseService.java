package com.day_walk.backend.domain.course.service;

import com.day_walk.backend.domain.course.bean.SaveCourseEntityBean;
import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.course.data.dto.in.SaveCourseDto;
import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


}
