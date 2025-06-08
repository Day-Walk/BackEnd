package com.day_walk.backend.domain.place_like.data;

import com.day_walk.backend.domain.place_like.data.in.PlaceLikeDto;
import com.day_walk.backend.global.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "place_like")
@Entity
public class PlaceLikeEntity extends BaseEntity {
    @Id
    private UUID id;
    private UUID userId;
    private UUID placeId;

    @Builder
    public PlaceLikeEntity(PlaceLikeDto savePlaceLikeDto) {
        this.id = UUID.randomUUID();
        this.userId = savePlaceLikeDto.getUserId();
        this.placeId = savePlaceLikeDto.getPlaceId();
    }
}
