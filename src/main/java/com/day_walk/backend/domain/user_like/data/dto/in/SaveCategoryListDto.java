package com.day_walk.backend.domain.user_like.data.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SaveCategoryListDto {
    private UUID categoryId;
    private List<UUID> tagList;
}
