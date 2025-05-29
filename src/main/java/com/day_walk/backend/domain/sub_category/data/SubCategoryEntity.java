package com.day_walk.backend.domain.sub_category.data;

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
@Table(name = "sub_category")
public class SubCategoryEntity extends BaseEntity {
    @Id
    private UUID id;
    private String name;

    private UUID categoryId;

    // Test Builder
    @Builder
    public SubCategoryEntity(String name, UUID categoryId) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.categoryId = categoryId;
    }
}
