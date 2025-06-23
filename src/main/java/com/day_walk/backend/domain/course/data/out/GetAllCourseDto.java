package com.day_walk.backend.domain.course.data.out;

import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.place.data.out.GetPlaceByCourseDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class GetAllCourseDto {
    private UUID courseId;
    private String title;
    private String userName;
    private List<GetPlaceByCourseDto> placeList;
    private boolean like;
    private int courseLike;
    private LocalDateTime createAt;

    @Builder
    public GetAllCourseDto(CourseEntity course, String userName, List<GetPlaceByCourseDto> placeList, boolean like, int courseLike) {
        this.courseId = course.getId();
        this.title = course.getTitle();
        this.userName = userName;
        this.placeList = placeList;
        this.like = like;
        this.courseLike = courseLike;
        this.createAt = course.getCreateAt();
    }
}
