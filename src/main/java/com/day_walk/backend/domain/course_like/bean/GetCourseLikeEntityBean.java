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
import java.util.stream.Collectors;
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
        CourseLikeEntity courseLike = courseLikeRepository.findByUserIdAndCourseId(courseLikeDto.getUserId(), courseLikeDto.getCourseId());
        if (courseLike != null) {
            Boolean existsInRedis = courseLikeRedisRepository.findCourseLike(courseLikeDto.getUserId(), courseLikeDto.getCourseId());
            return existsInRedis == null || Boolean.TRUE.equals(existsInRedis) ? courseLike : null;
        }

        if (Boolean.TRUE.equals(courseLikeRedisRepository.findCourseLike(courseLikeDto.getUserId(), courseLikeDto.getCourseId()))) {
            return new CourseLikeEntity(UUID.randomUUID(), courseLikeDto.getUserId(), courseLikeDto.getCourseId());
        }

        return null;
    }

    public List<CourseLikeEntity> exec(UUID userId) {
        Map<UUID, Boolean> redisLikeStates = courseLikeRedisRepository.findAllCourseLikeStates(userId);

        List<CourseLikeEntity> redisLikes = redisLikeStates.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(entry -> new CourseLikeEntity(UUID.randomUUID(), userId, entry.getKey()))
                .toList();

        List<CourseLikeEntity> mysqlLikes = courseLikeRepository.findAllByUserId(userId);

        Set<UUID> redisFalseIds = redisLikeStates.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        Set<String> uniqueKeys = new HashSet<>();
        List<CourseLikeEntity> merged = new ArrayList<>();

        for (CourseLikeEntity like : Stream.concat(mysqlLikes.stream(), redisLikes.stream()).toList()) {
            if (redisFalseIds.contains(like.getCourseId())) {
                continue;
            }
            String key = like.getUserId() + "-" + like.getCourseId();
            if (uniqueKeys.add(key)) {
                merged.add(like);
            }
        }

        return merged;
    }

    public int exec(CourseEntity course) {
        return courseLikeRepository.findAllByCourseId(course.getId()).size() + courseLikeRedisRepository.findAllLikedUserIds(course.getId()).size();
    }
}
