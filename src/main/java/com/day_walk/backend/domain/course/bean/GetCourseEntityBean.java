package com.day_walk.backend.domain.course.bean;

import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetCourseEntityBean {

    private final CourseRepository courseRepository;

    public CourseEntity exec(UUID courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }
}
