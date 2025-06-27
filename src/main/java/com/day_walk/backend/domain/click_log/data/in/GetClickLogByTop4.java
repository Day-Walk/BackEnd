package com.day_walk.backend.domain.click_log.data.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetClickLogByTop4 {
    private UUID uuid;
    private int clicks;
}
