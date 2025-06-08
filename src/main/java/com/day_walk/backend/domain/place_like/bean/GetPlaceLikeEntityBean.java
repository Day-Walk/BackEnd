package com.day_walk.backend.domain.place_like.bean;

import com.day_walk.backend.domain.place_like.data.PlaceLikeEntity;
import com.day_walk.backend.domain.place_like.repository.PlaceLikeRedisRepository;
import com.day_walk.backend.domain.place_like.repository.PlaceLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class GetPlaceLikeEntityBean {
    private final PlaceLikeRepository placeLikeRepository;
    private final PlaceLikeRedisRepository placeLikeRedisRepository;

    public List<PlaceLikeEntity> exec(UUID userId) {
        List<UUID> redisPlaceIds = placeLikeRedisRepository.findAllLikedPlaceIds(userId);

        List<PlaceLikeEntity> redisLikes = redisPlaceIds.stream()
                .map(placeId -> new PlaceLikeEntity(UUID.randomUUID(), userId, placeId))
                .toList();

        List<PlaceLikeEntity> mysqlLikes = placeLikeRepository.findAllByUserId(userId);

        Set<String> uniqueKeys = new HashSet<>();
        List<PlaceLikeEntity> merged = new ArrayList<>();

        for (PlaceLikeEntity like : Stream.concat(mysqlLikes.stream(), redisLikes.stream()).toList()) {
            String key = like.getUserId().toString() + "-" + like.getPlaceId().toString();
            if (uniqueKeys.add(key)) {
                merged.add(like);
            }
        }

        return merged;
    }

    public PlaceLikeEntity exec(UUID userId, UUID placeId) {
        return placeLikeRepository.findByUserIdAndPlaceId(userId, placeId);
    }
}
