package com.day_walk.backend.domain.course_like.data.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CourseLikeEvent {
    private UUID userId;
    private UUID courseId;
    private Boolean liked;
}
