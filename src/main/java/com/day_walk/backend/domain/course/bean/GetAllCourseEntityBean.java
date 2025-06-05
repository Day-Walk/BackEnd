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

    public List<CourseEntity> exec(String sortStr) {

        if (sortStr.equals("latest")) {
            return courseRepository.findAllByOrderByCreateAtDesc();
        } else if (sortStr.equals("like")) {
            return courseRepository.findAllOrderByLikeCount();
        }
        return courseRepository.findAll();
    }

}
