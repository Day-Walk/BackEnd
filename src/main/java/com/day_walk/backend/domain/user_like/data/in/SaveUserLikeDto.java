package com.day_walk.backend.domain.user_like.data.in;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SaveUserLikeDto {
    private UUID userId;
    private List<SaveCategoryListDto> categoryList;
}
