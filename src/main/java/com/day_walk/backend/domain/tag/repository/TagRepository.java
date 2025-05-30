package com.day_walk.backend.domain.tag.repository;

import com.day_walk.backend.domain.tag.data.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, UUID> {
    List<TagEntity> findAllByCategoryId(UUID categoryId);

    List<TagEntity> findByIdIn(List<UUID> idList);
}
