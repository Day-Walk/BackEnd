package com.day_walk.backend.domain.course.data.dto.in;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ModifyCourseTitleDto {

    private UUID courseId;
    private String title;
}
