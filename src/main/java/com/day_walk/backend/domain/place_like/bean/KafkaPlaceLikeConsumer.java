package com.day_walk.backend.domain.place_like.bean;

import com.day_walk.backend.domain.place_like.data.PlaceLikeEntity;
import com.day_walk.backend.domain.place_like.data.out.PlaceLikeEvent;
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
    private final GetPlaceLikeEntityBean getPlaceLikeEntityBean;

    @KafkaListener(topics = "save-place-like", groupId = "save-place-like", containerFactory = "placeLikeKafkaListenerContainerFactory")
    public void redisSaveConsume(PlaceLikeEvent placeLikeEvent) {
        placeLikeRedisRepository.savePlaceLike(placeLikeEvent.getUserId(), placeLikeEvent.getPlaceId(), placeLikeEvent.isLiked());
    }

    @KafkaListener(topics = "bulk-place-like", groupId = "bulk-place-like", containerFactory = "placeLikeKafkaListenerContainerFactory")
    public void mysqlConsume(PlaceLikeEvent placeLikeEvent) {
        if (placeLikeEvent.isLiked()) {
            PlaceLikeEntity placeLike = new PlaceLikeEntity(UUID.randomUUID(), placeLikeEvent.getUserId(), placeLikeEvent.getPlaceId());
            placeLikeRepository.save(placeLike);
        } else {
            PlaceLikeEntity placeLike = getPlaceLikeEntityBean.exec(placeLikeEvent.getUserId(), placeLikeEvent.getPlaceId());

            if (placeLike != null) {
                placeLikeRepository.delete(placeLike);
            }
        }

        placeLikeRedisRepository.deletePlaceLike(placeLikeEvent.getUserId(), placeLikeEvent.getPlaceId());
    }
}
