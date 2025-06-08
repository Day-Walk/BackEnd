package com.day_walk.backend.domain.place_like.bean;

import com.day_walk.backend.domain.place_like.data.PlaceLikeEntity;
import com.day_walk.backend.domain.place_like.data.in.PlaceLikeDto;
import com.day_walk.backend.domain.place_like.repository.PlaceLikeRedisRepository;
import com.day_walk.backend.domain.place_like.repository.PlaceLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class KafkaPlaceLikeConsumer {
    private final PlaceLikeRepository placeLikeRepository;
    private final PlaceLikeRedisRepository placeLikeRedisRepository;

    @KafkaListener(topics = "save-place-like", groupId = "save-place-like", containerFactory = "placeLikeKafkaListenerContainerFactory")
    public void redisSaveConsume(PlaceLikeDto placeLikeDto) {
        placeLikeRedisRepository.savePlaceLike(placeLikeDto.getUserId(), placeLikeDto.getPlaceId());
    }

    @KafkaListener(topics = "delete-place-like", groupId = "delete-place-like", containerFactory = "placeLikeKafkaListenerContainerFactory")
    public void redisDeleteConsume(PlaceLikeDto placeLikeDto) {
        placeLikeRedisRepository.deletePlaceLike(placeLikeDto.getUserId(), placeLikeDto.getPlaceId());
    }

    @KafkaListener(topics = "bulk-place-like", groupId = "bulk-place-like", containerFactory = "placeLikeKafkaListenerContainerFactory")
    public void mysqlConsume(PlaceLikeDto placeLikeDto) {
        PlaceLikeEntity placeLike = new PlaceLikeEntity(UUID.randomUUID(), placeLikeDto.getUserId(), placeLikeDto.getPlaceId());
        placeLikeRepository.save(placeLike);
        placeLikeRedisRepository.deletePlaceLike(placeLikeDto.getUserId(), placeLikeDto.getPlaceId());
    }
}
