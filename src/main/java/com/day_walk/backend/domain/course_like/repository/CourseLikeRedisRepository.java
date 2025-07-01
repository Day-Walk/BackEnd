package com.day_walk.backend.domain.course_like.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class CourseLikeRedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public void saveCourseLike(UUID userId, UUID courseId, boolean liked) {
        String key = "course-like:" + userId + ":" + courseId;
        redisTemplate.opsForValue().set(key, liked, Duration.ofDays(1));
    }

    public void deleteCourseLike(UUID userId, UUID courseId) {
        String key = "course-like:" + userId + ":" + courseId;
        redisTemplate.delete(key);
    }

    public Boolean findCourseLike(UUID userId, UUID courseId) {
        String key = "course-like:" + userId + ":" + courseId;
        return (Boolean) redisTemplate.opsForValue().get(key);
    }

    public List<UUID> findAllLikedUserIds(UUID courseId) {
        String pattern = "course-like:" + "*:" + courseId;
        Set<String> keys = redisTemplate.keys(pattern);

        if (keys == null) {
            return List.of();
        }

        return keys.stream()
                .filter(key -> {
                    Boolean value = (Boolean) redisTemplate.opsForValue().get(key);
                    return Boolean.TRUE.equals(value);
                })
                .map(key -> {
                    String[] parts = key.split(":");
                    return UUID.fromString(parts[1]);
                })
                .toList();
    }

    public Map<UUID, Boolean> findAllCourseLikeStates(UUID userId) {
        String pattern = "course-like:" + userId + ":*";
        Set<String> keys = redisTemplate.keys(pattern);

        if (keys == null || keys.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<UUID, Boolean> result = new HashMap<>();

        for (String key : keys) {
            Boolean value = (Boolean) redisTemplate.opsForValue().get(key);
            String[] parts = key.split(":");
            if (parts.length >= 3) {
                try {
                    UUID courseId = UUID.fromString(parts[2]);
                    result.put(courseId, Boolean.TRUE.equals(value));
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid UUID in Redis key: " + key);
                }
            }
        }

        return result;
    }
}
