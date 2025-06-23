package com.day_walk.backend.domain.course.bean;

import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GetAllCourseEntityBean {
    private final CourseRepository courseRepository;

    public List<CourseEntity> exec() {
        return courseRepository.findAll();
    }
}
