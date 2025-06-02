package com.day_walk.backend.domain.course_like.bean;

import com.day_walk.backend.domain.course_like.data.CourseLikeEntity;
import com.day_walk.backend.domain.course_like.data.in.DeleteCourseLikeDto;
import com.day_walk.backend.domain.course_like.data.in.SaveCourseLikeDto;
import com.day_walk.backend.domain.course_like.repository.CourseLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetCourseLikeEntityBean {
    private final CourseLikeRepository courseLikeRepository;

    public CourseLikeEntity exec(SaveCourseLikeDto saveCourseLikeDto) {
        return courseLikeRepository.findByUserIdAndCourseId(saveCourseLikeDto.getUserId(), saveCourseLikeDto.getCourseId());
    }

    public CourseLikeEntity exec(DeleteCourseLikeDto deleteCourseLikeDto) {
        return courseLikeRepository.findById(deleteCourseLikeDto.getCourseLikeId()).orElse(null);
    }
}
