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

    // 특정 유저의 코스를 최신순으로 정렬
    List<CourseEntity> findAllByUserIdOrderByCreateAtDesc(UUID userId);

    // Native: 제목 또는 사용자 이름에 키워드 포함 검색
    @Query(value = """
        SELECT c.*
        FROM course c
        LEFT JOIN user u ON c.user_id = u.id
        WHERE c.title LIKE %:searchStr%
           OR u.name LIKE %:searchStr%
    """, nativeQuery = true)
    List<CourseEntity> findByTitleOrUserName(@Param("searchStr") String searchStr);

    // Native: 제목 또는 사용자 이름 포함 + 최신순 정렬
    @Query(value = """
        SELECT c.*
        FROM course c
        LEFT JOIN user u ON c.user_id = u.id
        WHERE c.title LIKE %:searchStr%
           OR u.name LIKE %:searchStr%
        ORDER BY c.create_at DESC
    """, nativeQuery = true)
    List<CourseEntity> searchByTitleOrUserName(@Param("searchStr") String searchStr);

    // Native: 제목 또는 사용자 이름 포함 + 좋아요 수 기준 정렬
    @Query(value = """
                SELECT c.*
                FROM course c
                LEFT JOIN course_like cl ON c.id = cl.course_id
                LEFT JOIN user u ON c.user_id = u.id
                WHERE c.title LIKE %:searchStr%
                   OR u.name LIKE %:searchStr%
                GROUP BY c.id
                ORDER BY COUNT(cl.id) DESC
            """, nativeQuery = true)
    List<CourseEntity> findByTitleOrUserNameOrderByLikeCount(@Param("searchStr") String searchStr);
}
