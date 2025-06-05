package com.day_walk.backend.domain.tag.data.out;

import com.day_walk.backend.domain.tag.data.TagEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetTagByReviewDto {
    private UUID tagId;
    private String fullName;

    @Builder
    public GetTagByReviewDto(TagEntity tag) {
        this.tagId = tag.getId();
        this.fullName = tag.getFullName();
    }
}
