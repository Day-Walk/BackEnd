package com.day_walk.backend.domain.course.data.dto.out;

import com.day_walk.backend.domain.place.data.out.GetPlaceDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class GetCourseDto {

    private String userName;
    private String title;
    private List<GetPlaceDto> placeList;
    private boolean like;
    private int courseLike;

    @Builder
    public GetCourseDto(String userName, String title, List<GetPlaceDto> placeList, boolean like, int courseLike) {
        this.userName = userName;
        this.title = title;
        this.placeList = placeList;
        this.like = like;
        this.courseLike = courseLike;
    }
}
