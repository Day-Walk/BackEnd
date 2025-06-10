package com.day_walk.backend.domain.place.repository;

import com.day_walk.backend.domain.place.data.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, UUID> {
    List<PlaceEntity> findByNameContaining(String searchStr);
}
