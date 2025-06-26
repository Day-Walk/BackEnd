package com.day_walk.backend.domain.course.data.out;

import com.day_walk.backend.domain.place.data.out.GetPlaceWithStarDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class GetCourseDto {

    private String userName;
    private String title;
    private List<GetPlaceWithStarDto> placeList;
    private boolean like;
    private int courseLike;
    private LocalDateTime createAt;

    @Builder
    public GetCourseDto(String userName, String title, LocalDateTime createAt, List<GetPlaceWithStarDto> placeList, boolean like, int courseLike) {
        this.userName = userName;
        this.title = title;
        this.createAt = createAt;
        this.placeList = placeList;
        this.like = like;
        this.courseLike = courseLike;
    }
}
