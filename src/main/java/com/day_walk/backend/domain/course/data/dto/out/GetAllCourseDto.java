package com.day_walk.backend.domain.course.data.dto.out;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class GetAllCourseDto {
    private UUID courseId;
    private String title;
    private String userName;
    // 추후 추가할 data
//    private List<GetPlaceByCourseDto> placeList; // location, cat, subCat 불포함 placeDto
//    private int courseLike;  //코스 좋아요
//     private boolean like;

    @Builder
    public GetAllCourseDto(UUID courseId, String title, String userName) {
        this.courseId = courseId;
        this.title = title;
        this.userName = userName;
    }
}


