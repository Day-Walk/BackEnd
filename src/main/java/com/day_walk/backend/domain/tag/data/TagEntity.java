package com.day_walk.backend.domain.tag.data;

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
@Entity
@Table(name = "tag")
public class TagEntity extends BaseEntity {
    @Id
    private UUID id;
    String code;
    String fullName;
    String keyword;

    private UUID categoryId;

    @Builder
    public TagEntity(String code, String fullName, String keyword, UUID categoryId) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.fullName = fullName;
        this.keyword = keyword;
        this.categoryId = categoryId;
    }
}
