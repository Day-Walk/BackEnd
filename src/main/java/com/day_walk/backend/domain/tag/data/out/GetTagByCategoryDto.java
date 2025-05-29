package com.day_walk.backend.domain.tag.data.out;

import com.day_walk.backend.domain.tag.data.TagEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetTagByCategoryDto {
    private UUID tagId;
    private String keyword;

    @Builder
    public GetTagByCategoryDto(TagEntity tag) {
        this.tagId = tag.getId();
        this.keyword = tag.getKeyword();
    }
}
