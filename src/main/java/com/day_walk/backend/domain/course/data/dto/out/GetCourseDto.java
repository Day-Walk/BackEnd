package com.day_walk.backend.domain.course.data.dto.out;

import com.day_walk.backend.domain.place.data.out.GetPlaceDto;
import jakarta.persistence.Convert;
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
//    private boolean like 추가 예정;

    @Builder
    public GetCourseDto(String userName, String title, List<GetPlaceDto> placeList) {
        this.userName = userName;
        this.title = title;
        this.placeList = placeList;
    }
}
//, List<GetPlaceDto> placeList
