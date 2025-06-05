package com.day_walk.backend.domain.course.repository;

import com.day_walk.backend.domain.course.data.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {

    List<CourseEntity> findAllByUserId(UUID userId);

    List<CourseEntity> findByTitleContaining(String searchStr);

    List<CourseEntity> findByTitleContainingOrderByCreateAtDesc(String searchStr);

    @Query(value = """
                SELECT c.*
                FROM course c
                LEFT JOIN course_like cl ON c.id = cl.course_id
                WHERE c.title LIKE %:searchStr%
                GROUP BY c.id
                ORDER BY COUNT(cl.id) DESC
            """, nativeQuery = true)
    List<CourseEntity> findByTitleOrderByLikeCount(@Param("searchStr") String searchStr);

    List<CourseEntity> findAllByOrderByCreateAtDesc();

    @Query(value = """
                SELECT c.*
                FROM course c
                LEFT JOIN course_like cl ON c.id = cl.course_id
                GROUP BY c.id
                ORDER BY COUNT(cl.id) DESC
            """, nativeQuery = true)
    List<CourseEntity> findAllOrderByLikeCount();

    List<CourseEntity> findAllByUserIdOrderByCreateAtDesc(UUID userId);

}
