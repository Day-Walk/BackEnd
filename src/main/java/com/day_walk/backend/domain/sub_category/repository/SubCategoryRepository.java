package com.day_walk.backend.domain.sub_category.repository;

import com.day_walk.backend.domain.sub_category.data.SubCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity, UUID> {
}
