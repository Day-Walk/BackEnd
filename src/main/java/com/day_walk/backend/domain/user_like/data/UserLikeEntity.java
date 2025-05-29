package com.day_walk.backend.domain.user_like.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_like")
public class UserLikeEntity {
    @Id
    private UUID id;
    private UUID userId;
    // categoryId 추가 예정
    // List<UUID>tagList 추가 예정

    @Builder
    public UserLikeEntity(UUID id, UUID userId) {
        this.id = id;
        this.userId = userId;
    }

}
