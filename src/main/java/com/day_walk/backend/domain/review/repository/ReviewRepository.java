package com.day_walk.backend.domain.review.repository;

import com.day_walk.backend.domain.review.data.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {
    List<ReviewEntity> findAllByPlaceIdAndHasDelete(UUID placeId, boolean hasDelete);

    List<ReviewEntity> findAllByUserIdAndHasDelete(UUID userId, boolean hasDelete);
}
