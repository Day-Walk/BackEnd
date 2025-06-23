package com.day_walk.backend.domain.click_log.data.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SaveClickLogByElkDto {
    private UUID userId;
    private UUID placeId;
    private String timestamp;
}
