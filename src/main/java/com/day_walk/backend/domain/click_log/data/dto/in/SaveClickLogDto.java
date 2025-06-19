package com.day_walk.backend.domain.click_log.data.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SaveClickLogDto {

    private UUID userId;
    private UUID placeId;
    private String timestamp;

}
