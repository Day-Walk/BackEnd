package com.day_walk.backend.domain.course.repository;

import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.user.data.UserEntity; // 쿼리 안에서 사용 중 삭제 금지
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {

    // 특정 유저의 모든 코스 조회
    List<CourseEntity> findAllByUserId(UUID userId);

    // 특정 유저의 코스를 최신순으로 정렬
    List<CourseEntity> findAllByUserIdOrderByCreateAtDesc(UUID userId);

    // 전체 코스를 생성일 기준 최신순 정렬
    List<CourseEntity> findAllByOrderByCreateAtDesc();

    // 제목 키워드 포함 검색
    List<CourseEntity> findByTitleContaining(String searchStr);

    // 제목 키워드 포함 + 최신순 정렬
    List<CourseEntity> findByTitleContainingOrderByCreateAtDesc(String searchStr);

    // JPQL: 제목 또는 사용자 이름에 키워드 포함 검색
    @Query("""
        SELECT c FROM CourseEntity c, UserEntity u
        WHERE c.title LIKE %:searchStr%
           OR u.name LIKE %:searchStr%
    """)
    List<CourseEntity> findByTitleOrUserName(@Param("searchStr") String searchStr);

    // JPQL: 제목 또는 사용자 이름 키워드 포함 + 최신순 정렬
    @Query("""
        SELECT c FROM CourseEntity c, UserEntity u
        WHERE c.title LIKE %:searchStr%
           OR u.name LIKE %:searchStr%
        ORDER BY c.createAt DESC
    """)
    List<CourseEntity> searchByTitleOrUserName(@Param("searchStr") String searchStr);

    // Native: 제목 키워드 포함 + 좋아요 수 기준 정렬
    @Query(value = """
        SELECT c.*
        FROM course c
        LEFT JOIN course_like cl ON c.id = cl.course_id
        WHERE c.title LIKE %:searchStr%
        GROUP BY c.id
        ORDER BY COUNT(cl.id) DESC
    """, nativeQuery = true)
    List<CourseEntity> findByTitleOrderByLikeCount(@Param("searchStr") String searchStr);

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

    // Native: 전체 코스 좋아요 수 기준 정렬
    @Query(value = """
        SELECT c.*
        FROM course c
        LEFT JOIN course_like cl ON c.id = cl.course_id
        GROUP BY c.id
        ORDER BY COUNT(cl.id) DESC
    """, nativeQuery = true)
    List<CourseEntity> findAllOrderByLikeCount();
}
