package com.day_walk.backend.domain.course.data.out;

import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.place.data.out.GetPlaceByCourseDto;
import com.day_walk.backend.domain.user.data.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetCourseByLikeDto {
    private UUID courseId;
    private String title;
    private String userName;
    private int courseLike;
    private List<GetPlaceByCourseDto> placeList;

    @Builder
    public GetCourseByLikeDto(CourseEntity course, UserEntity user, int courseLike, List<GetPlaceByCourseDto> placeList) {
        this.courseId = course.getId();
        this.title = course.getTitle();
        this.userName = user.getName();
        this.courseLike = courseLike;
        this.placeList = placeList;
    }
}
