package com.day_walk.backend.domain.user_like.data;

import com.day_walk.backend.domain.user_like.data.dto.in.SaveUserTagDto;
import com.day_walk.backend.global.util.StringToListConverter;
import jakarta.persistence.Convert;
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
    private String category;

    @Convert(converter = StringToListConverter.class)
    private List<String> tagList;

    // test builder
    @Builder
    public UserLikeEntity(UUID id, UUID userId) {
        this.id = id;
        this.userId = userId;
    }

    public UserLikeEntity(UUID userId, SaveUserTagDto saveUserTagDto) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.category = saveUserTagDto.getCategoryName();
        this.tagList = saveUserTagDto.getTagList();
    }
}
