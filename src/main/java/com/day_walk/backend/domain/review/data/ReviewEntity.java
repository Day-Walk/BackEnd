package com.day_walk.backend.domain.review.data;

import com.day_walk.backend.domain.review.data.in.SaveReviewDto;
import com.day_walk.backend.global.BaseEntity;
import com.day_walk.backend.global.util.StringToListConverter;
import com.day_walk.backend.global.util.StringToUUIDListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "review")
public class ReviewEntity extends BaseEntity {
    @Id
    private UUID id;
    private double stars;
    private String imgUrl;

    @Lob
    private String content;

    private boolean hasDelete;

    private UUID userId;
    private UUID placeId;

    @Lob
    @Convert(converter = StringToUUIDListConverter.class)
    private List<UUID> tagList;

    public void delete() {
        this.hasDelete = true;
    }

    @Builder
    public ReviewEntity(SaveReviewDto saveReviewDto) {
        this.id = UUID.randomUUID();
        this.stars = saveReviewDto.getStars();
        this.imgUrl = saveReviewDto.getImgUrl();
        this.content = saveReviewDto.getContent();
        this.hasDelete = false;
        this.userId = saveReviewDto.getUserId();
        this.placeId = saveReviewDto.getPlaceId();
        this.tagList = saveReviewDto.getTagList();
    }
}
