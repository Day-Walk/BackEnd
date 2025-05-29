package com.day_walk.backend.domain.user_like.repository;

import com.day_walk.backend.domain.user_like.data.UserLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLikeEntity, UUID> {
}
