package com.day_walk.backend.domain.user_like.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_like")
public class UserLikeEntity {
    @Id
    private UUID id;
    private UUID userId;
    private UUID categoryId;
    private List<UUID> tagList;

    @Builder
    public UserLikeEntity(UUID id, UUID userId, UUID categoryId, List<UUID> tagList) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.tagList = tagList;
    }

}
