package com.day_walk.backend.domain.place_like.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class PlaceLikeRedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public void savePlaceLike(UUID userId, UUID placeId, boolean liked) {
        String key = "place-like:" + userId + ":" + placeId;
        redisTemplate.opsForValue().set(key, liked, Duration.ofDays(1));
    }

    public void deletePlaceLike(UUID userId, UUID placeId) {
        String key = "place-like:" + userId + ":" + placeId;
        redisTemplate.delete(key);
    }

    public Boolean findPlaceLike(UUID userId, UUID placeId) {
        String key = "place-like:" + userId + ":" + placeId;
        return (Boolean) redisTemplate.opsForValue().get(key);
    }

    public Map<UUID, Boolean> findAllPlaceLikeStates(UUID userId) {
        String pattern = "place-like:" + userId + ":*";
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
                    UUID placeId = UUID.fromString(parts[2]);
                    result.put(placeId, Boolean.TRUE.equals(value));
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid UUID in Redis key: " + key);
                }
            }
        }

        return result;
    }
}
