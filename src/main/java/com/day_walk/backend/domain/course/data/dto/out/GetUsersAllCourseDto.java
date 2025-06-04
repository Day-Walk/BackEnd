package com.day_walk.backend.domain.course.data.dto.out;

import com.day_walk.backend.domain.place.data.out.GetPlaceByCourseDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class GetUsersAllCourseDto {
    private UUID courseId;
    private String title;
    private boolean visible;
    private List<GetPlaceByCourseDto> placeList;
}
