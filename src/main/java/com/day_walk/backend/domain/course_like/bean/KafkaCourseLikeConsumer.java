package com.day_walk.backend.domain.course_like.bean;

import com.day_walk.backend.domain.course_like.data.CourseLikeEntity;
import com.day_walk.backend.domain.course_like.data.out.CourseLikeEvent;
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
    public void redisSaveConsume(CourseLikeEvent courseLikeEvent) {
        courseLikeRedisRepository.saveCourseLike(courseLikeEvent.getUserId(), courseLikeEvent.getCourseId(), courseLikeEvent.getLiked());
    }

    @KafkaListener(topics = "bulk-course-like", groupId = "bulk-course-like", containerFactory = "courseLikeKafkaListenerContainerFactory")
    public void mysqlConsume(CourseLikeEvent courseLikeEvent) {
        if (Boolean.TRUE.equals(courseLikeEvent.getLiked())) {
            CourseLikeEntity courseLike = new CourseLikeEntity(UUID.randomUUID(), courseLikeEvent.getUserId(), courseLikeEvent.getCourseId());
            courseLikeRepository.save(courseLike);
        } else {
            CourseLikeEntity courseLike = courseLikeRepository.findByUserIdAndCourseId(courseLikeEvent.getUserId(), courseLikeEvent.getCourseId());

            if (courseLike != null) {
                courseLikeRepository.delete(courseLike);
            }
        }

        courseLikeRedisRepository.deleteCourseLike(courseLikeEvent.getUserId(), courseLikeEvent.getCourseId());
    }
}
