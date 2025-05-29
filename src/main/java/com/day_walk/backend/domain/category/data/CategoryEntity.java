package com.day_walk.backend.domain.category.data;

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
@Table(name = "category")
public class CategoryEntity extends BaseEntity {
    @Id
    private UUID id;
    private String name;

    @Builder
    public CategoryEntity(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
