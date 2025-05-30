package com.day_walk.backend.domain.place.bean;

import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GetPlaceEntityBean {
    private final PlaceRepository placeRepository;

    public PlaceEntity exec(UUID placeId) {
        return placeRepository.findById(placeId).orElse(null);
    }

    public List<PlaceEntity> exec(String searchStr) {
        return placeRepository.findByNameContains(searchStr);
    }
}
