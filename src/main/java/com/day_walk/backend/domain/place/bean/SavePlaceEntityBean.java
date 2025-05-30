package com.day_walk.backend.domain.place.bean;

import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SavePlaceEntityBean {
    private final PlaceRepository placeRepository;

    public void exec(PlaceEntity place) {
        placeRepository.save(place);
    }
}
