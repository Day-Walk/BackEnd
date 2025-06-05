package com.day_walk.backend.domain.course_like.bean;

import com.day_walk.backend.domain.course_like.data.CourseLikeEntity;
import com.day_walk.backend.domain.course_like.repository.CourseLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteCourseLikeEntityBean {
    private final CourseLikeRepository courseLikeRepository;

    public void exec(CourseLikeEntity courseLike) {
        courseLikeRepository.delete(courseLike);
    }
}
