package com.day_walk.backend.domain.crowd_level.data.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MlCrowdResponseDto {

    private boolean success;
    private String message;
    private GetCrowdLevelDto crowdLevel;
}
