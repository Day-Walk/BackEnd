package com.day_walk.backend.domain.click_log.data;

import com.day_walk.backend.domain.click_log.data.in.SaveClickLogDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class ClickLogEntity {
    @Id
    private UUID id;
    private UUID userId;
    private UUID placeId;
    private LocalDateTime createAt;

    @Builder
    public ClickLogEntity(SaveClickLogDto saveClickLogDto) {
        this.id = UUID.randomUUID();
        this.userId = saveClickLogDto.getUserId();
        this.placeId = saveClickLogDto.getPlaceId();
        this.createAt = LocalDateTime.now();
    }
}
