package com.day_walk.backend.domain.place.data.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetPlaceDetailByMlDto {
    private UUID id;
    private String name;
    private String category;
    private String subcategory;
}
