package com.day_walk.backend.domain.course.data.dto.out;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GetSearchCourseDto {
    private UUID courseId;
    private String title;
    private String userName;
    // 추후 추가 예정 data
//    private List<GetPlaceByCourseDto> placeInfo;
//    private int courseLike;
//    private boolean like;

}
