package com.day_walk.backend.domain.course_like.data.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeleteCourseLikeDto {
    private UUID courseLikeId;
}
