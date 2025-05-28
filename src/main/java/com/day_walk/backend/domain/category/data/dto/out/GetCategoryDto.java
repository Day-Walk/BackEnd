package com.day_walk.backend.domain.category.data.dto.out;

import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.tag.data.out.GetTagByCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetCategoryDto {
    private UUID categoryId;
    private String categoryName;
    private List<GetTagByCategoryDto> tagList;

    @Builder
    public GetCategoryDto(CategoryEntity category, List<GetTagByCategoryDto> tagList) {
        this.categoryId = category.getId();
        this.categoryName = category.getName();
        this.tagList = tagList;
    }
}
