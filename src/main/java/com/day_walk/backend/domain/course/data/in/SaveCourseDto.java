package com.day_walk.backend.domain.course.data.in;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SaveCourseDto {

    private UUID userId;
    private String title;
    private boolean visible;
    private List<UUID> placeList;
}
