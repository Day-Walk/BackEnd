package com.day_walk.backend.domain.crowd_level.data.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCrowdLevelDto {
    private int total;
    private List<GetCrowdLevelInfoDto> row;
}
