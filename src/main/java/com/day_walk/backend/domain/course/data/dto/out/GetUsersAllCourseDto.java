package com.day_walk.backend.domain.course.data.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GetUsersAllCourseDto {
    private UUID courseId;
    private String title;
    private boolean visible;
//    private List<GetPlaceByCourse> placeList;
}
