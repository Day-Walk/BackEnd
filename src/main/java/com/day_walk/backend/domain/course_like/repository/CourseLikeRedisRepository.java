package com.day_walk.backend.domain.course_like.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CourseLikeRedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public void saveCourseLike(UUID userId, UUID courseId) {
        String key = "course-like:" + userId + ":" + courseId;
        redisTemplate.opsForValue().set(key, true, Duration.ofDays(1));
    }

    public void deleteCourseLike(UUID userId, UUID courseId) {
        String key = "course-like:" + userId + ":" + courseId;
        redisTemplate.delete(key);
    }

    public List<UUID> findAllLikedCourseIds(UUID userId) {
        String pattern = "course-like:" + userId + ":*";
        Set<String> keys = redisTemplate.keys(pattern);

        if (keys == null) {
            return List.of();
        }

        return keys.stream()
                .map(key -> {
                    String[] parts = key.split(":");
                    return UUID.fromString(parts[2]);
                })
                .toList();
    }
}
