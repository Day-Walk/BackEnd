package com.day_walk.backend.domain.course_like.bean;

import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.course_like.data.CourseLikeEntity;
import com.day_walk.backend.domain.course_like.data.in.CourseLikeDto;
import com.day_walk.backend.domain.course_like.data.out.CourseLikeEvent;
import com.day_walk.backend.domain.course_like.repository.CourseLikeRedisRepository;
import com.day_walk.backend.domain.course_like.repository.CourseLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class GetCourseLikeEntityBean {
    private final CourseLikeRepository courseLikeRepository;
    private final CourseLikeRedisRepository courseLikeRedisRepository;

    public CourseLikeEntity exec(CourseLikeEvent courseLikeEvent) {
        return courseLikeRepository.findByUserIdAndCourseId(courseLikeEvent.getUserId(), courseLikeEvent.getCourseId());
    }

    public CourseLikeEntity exec(CourseLikeDto courseLikeDto) {
        return courseLikeRepository.findByUserIdAndCourseId(courseLikeDto.getUserId(), courseLikeDto.getCourseId());
    }

    public List<CourseLikeEntity> exec(UUID userId) {
        List<UUID> redisCourseIds = courseLikeRedisRepository.findAllLikedCourseIds(userId);

        List<CourseLikeEntity> redisLikes = redisCourseIds.stream()
                .map(courseId -> new CourseLikeEntity(UUID.randomUUID(), userId, courseId))
                .toList();

        List<CourseLikeEntity> mysqlLikes = courseLikeRepository.findAllByUserId(userId);

        Set<String> uniqueKeys = new HashSet<>();
        List<CourseLikeEntity> merged = new ArrayList<>();

        for (CourseLikeEntity like : Stream.concat(mysqlLikes.stream(), redisLikes.stream()).toList()) {
            String key = like.getUserId().toString() + "-" + like.getCourseId().toString();
            if (uniqueKeys.add(key)) {
                merged.add(like);
            }
        }

        return merged;
    }

    public int exec(CourseEntity course) {
        return courseLikeRepository.findAllByCourseId(course.getId()).size();
    }
}
