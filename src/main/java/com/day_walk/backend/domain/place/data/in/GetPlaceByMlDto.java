package com.day_walk.backend.domain.place.data.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetPlaceByMlDto {
    private boolean success;
    private GetPlaceListByMlDto places;
}
