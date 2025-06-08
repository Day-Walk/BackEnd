package com.day_walk.backend.domain.course_like.bean;

import com.day_walk.backend.domain.course_like.data.CourseLikeEntity;
import com.day_walk.backend.domain.course_like.data.in.CourseLikeDto;
import com.day_walk.backend.domain.course_like.repository.CourseLikeRedisRepository;
import com.day_walk.backend.domain.course_like.repository.CourseLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class KafkaCourseLikeConsumer {
    private final CourseLikeRepository courseLikeRepository;
    private final CourseLikeRedisRepository courseLikeRedisRepository;

    @KafkaListener(topics = "save-course-like", groupId = "save-course-like", containerFactory = "courseLikeKafkaListenerContainerFactory")
    public void redisSaveConsume(CourseLikeDto courseLikeDto) {
        courseLikeRedisRepository.saveCourseLike(courseLikeDto.getUserId(), courseLikeDto.getCourseId());
    }

    @KafkaListener(topics = "delete-course-like", groupId = "delete-course-like", containerFactory = "courseLikeKafkaListenerContainerFactory")
    public void redisDeleteConsume(CourseLikeDto courseLikeDto) {
        courseLikeRedisRepository.deleteCourseLike(courseLikeDto.getUserId(), courseLikeDto.getCourseId());
    }

    @KafkaListener(topics = "bulk-course-like", groupId = "bulk-course-like", containerFactory = "courseLikeKafkaListenerContainerFactory")
    public void mysqlConsume(CourseLikeDto courseLikeDto) {
        CourseLikeEntity courseLike = new CourseLikeEntity(UUID.randomUUID(), courseLikeDto.getUserId(), courseLikeDto.getCourseId());
        courseLikeRepository.save(courseLike);
        courseLikeRedisRepository.deleteCourseLike(courseLikeDto.getUserId(), courseLikeDto.getCourseId());
    }
}
