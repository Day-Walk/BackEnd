package com.day_walk.backend.domain.place.data.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetPlaceListByMlDto {
    private List<GetPlaceDetailByMlDto> recommend;
    private List<GetPlaceDetailByMlDto> normal;
}
