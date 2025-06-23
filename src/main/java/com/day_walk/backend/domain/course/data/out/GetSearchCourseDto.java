package com.day_walk.backend.domain.course.data.out;

import com.day_walk.backend.domain.place.data.out.GetPlaceByCourseDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class GetSearchCourseDto {
    private UUID courseId;
    private String title;
    private String userName;
    private List<GetPlaceByCourseDto> placeList;
    private int courseLike;
    private boolean like;

}
