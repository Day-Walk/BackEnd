package com.day_walk.backend.domain.click_log.repository;

import com.day_walk.backend.domain.click_log.data.ClickLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClickLogRepository extends JpaRepository<ClickLogEntity, UUID> {
}
