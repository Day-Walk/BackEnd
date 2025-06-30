package com.day_walk.backend.domain.course.bean;

import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetSearchCourseEntityBean {
    private final CourseRepository courseRepository;

    public List<CourseEntity> exec(String searchStr) {
        return courseRepository.findByTitleOrUserName(searchStr);
    }
}
