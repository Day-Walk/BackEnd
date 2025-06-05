package com.day_walk.backend.domain.place.data.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetPlaceBySearchListDto {
    private List<GetPlaceBySearchDto> recommendList;
    private List<GetPlaceBySearchDto> placeList;
}
