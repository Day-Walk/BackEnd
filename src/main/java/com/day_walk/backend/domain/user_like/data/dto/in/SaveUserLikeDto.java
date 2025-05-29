package com.day_walk.backend.domain.user_like.data.dto.in;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class SaveUserLikeDto {
    private UUID userId;
    private List<SaveUserTagDto> categoryList;
}
