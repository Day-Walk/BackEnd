package com.day_walk.backend.domain.place_like.bean;

import com.day_walk.backend.domain.place_like.data.PlaceLikeEntity;
import com.day_walk.backend.domain.place_like.repository.PlaceLikeRedisRepository;
import com.day_walk.backend.domain.place_like.repository.PlaceLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class GetPlaceLikeEntityBean {
    private final PlaceLikeRepository placeLikeRepository;
    private final PlaceLikeRedisRepository placeLikeRedisRepository;

    public List<PlaceLikeEntity> exec(UUID userId) {
        Map<UUID, Boolean> redisLikeStates = placeLikeRedisRepository.findAllPlaceLikeStates(userId);

        List<PlaceLikeEntity> redisLikes = redisLikeStates.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(entry -> new PlaceLikeEntity(UUID.randomUUID(), userId, entry.getKey()))
                .toList();

        List<PlaceLikeEntity> mysqlLikes = placeLikeRepository.findAllByUserId(userId);

        Set<UUID> redisFalseIds = redisLikeStates.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        Set<String> uniqueKeys = new HashSet<>();
        List<PlaceLikeEntity> merged = new ArrayList<>();

        for (PlaceLikeEntity like : Stream.concat(mysqlLikes.stream(), redisLikes.stream()).toList()) {
            if (redisFalseIds.contains(like.getPlaceId())) {
                continue;
            }
            String key = like.getUserId() + "-" + like.getPlaceId();
            if (uniqueKeys.add(key)) {
                merged.add(like);
            }
        }

        return merged;
    }

    public PlaceLikeEntity exec(UUID userId, UUID placeId) {
        PlaceLikeEntity placeLike = placeLikeRepository.findByUserIdAndPlaceId(userId, placeId);
        if (placeLike != null) {
            return placeLike;
        }

        if (placeLikeRedisRepository.findPlaceLike(userId, placeId)) {
            return new PlaceLikeEntity(UUID.randomUUID(), userId, placeId);
        }

        return null;
    }
}
