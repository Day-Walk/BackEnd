package com.day_walk.backend.domain.course_like.data;

import com.day_walk.backend.domain.course_like.data.in.CourseLikeDto;
import com.day_walk.backend.global.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "course_like")
public class CourseLikeEntity extends BaseEntity {
    @Id
    private UUID id;
    private UUID userId;
    private UUID courseId;

    @Builder
    public CourseLikeEntity(CourseLikeDto saveCourseLikeDto) {
        this.id = UUID.randomUUID();
        this.userId = saveCourseLikeDto.getUserId();
        this.courseId = saveCourseLikeDto.getCourseId();
    }
}
