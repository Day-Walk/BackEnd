package com.day_walk.backend.domain.course.data.dto.out;

import com.day_walk.backend.domain.place.data.out.GetPlaceByCourseDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class GetAllCourseDto {
    private UUID courseId;
    private String title;
    private String userName;
    // 추후 추가할 data
    private List<GetPlaceByCourseDto> placeList;
    private boolean like;
    private int courseLike;

    @Builder
    public GetAllCourseDto(UUID courseId, String title, String userName, List<GetPlaceByCourseDto> placeList, boolean like, int courseLike) {
        this.courseId = courseId;
        this.title = title;
        this.userName = userName;
        this.placeList = placeList;
        this.like = like;
        this.courseLike = courseLike;
    }
}


