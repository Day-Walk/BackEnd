package com.day_walk.backend.domain.place_like.bean;

import com.day_walk.backend.domain.place_like.data.PlaceLikeEntity;
import com.day_walk.backend.domain.place_like.data.in.DeletePlaceLikeDto;
import com.day_walk.backend.domain.place_like.repository.PlaceLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GetPlaceLikeEntityBean {
    private final PlaceLikeRepository placeLikeRepository;

    public PlaceLikeEntity exec(DeletePlaceLikeDto deletePlaceLikeDto) {
        return placeLikeRepository.findById(deletePlaceLikeDto.getPlaceLikeId()).orElse(null);
    }

    public List<PlaceLikeEntity> exec(UUID userId) {
        return placeLikeRepository.findAllByUserId(userId);
    }
}
