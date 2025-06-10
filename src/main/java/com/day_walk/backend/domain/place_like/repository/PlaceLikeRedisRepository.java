package com.day_walk.backend.domain.place_like.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    public boolean findPlaceLike(UUID userId, UUID placeId) {
        String key = "place-like:" + userId + ":" + placeId;
        Boolean value = (Boolean) redisTemplate.opsForValue().get(key);
        return Boolean.TRUE.equals(value);
    }

    public List<UUID> findAllLikedPlaceIds(UUID userId) {
        String pattern = "place-like:" + userId + ":*";
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
                    return UUID.fromString(parts[2]);
                })
                .toList();
    }
}
