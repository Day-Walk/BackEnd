package com.day_walk.backend.domain.user.data.dto.out;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GetUserDto {
    private UUID userId;
    private String name;

}
