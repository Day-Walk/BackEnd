package com.day_walk.backend.domain.course.repository;

import com.day_walk.backend.domain.course.data.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
}
