package com.day_walk.backend.domain.place_like.repository;

import com.day_walk.backend.domain.place_like.data.PlaceLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceLikeRepository extends JpaRepository<PlaceLikeEntity, UUID> {
    List<PlaceLikeEntity> findAllByUserId(UUID userId);
}
