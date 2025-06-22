package com.day_walk.backend.domain.user.data.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class GetCrowdLevelInfoDto {
    private String area_nm;
    private BigDecimal x;
    private BigDecimal y;
    private String area_congest_lvl;
    private int area_congest_num;
}
