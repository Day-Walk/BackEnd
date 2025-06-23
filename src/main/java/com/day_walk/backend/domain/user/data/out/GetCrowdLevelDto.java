package com.day_walk.backend.domain.user.data.out;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetCrowdLevelDto {
    private int total;
    private List<GetCrowdLevelInfoDto> row;
}
