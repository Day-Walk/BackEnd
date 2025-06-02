package com.day_walk.backend.domain.course.bean;

import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.course.repository.CourseRepository;
import com.day_walk.backend.domain.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetUsersAllCourseEntityBean {
    private final CourseRepository courseRepository;
    public List<CourseEntity> exec(UUID userId) {
        return courseRepository.findAllByUserId(userId);
    }
}
