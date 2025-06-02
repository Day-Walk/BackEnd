package com.day_walk.backend.domain.course_like.repository;

import com.day_walk.backend.domain.course_like.data.CourseLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseLikeRepository extends JpaRepository<CourseLikeEntity, UUID> {
    CourseLikeEntity findByUserIdAndCourseId(UUID userId, UUID courseId);

    List<CourseLikeEntity> findAllByUserId(UUID userId);

    List<CourseLikeEntity> findAllByCourseId(UUID courseId);
}
