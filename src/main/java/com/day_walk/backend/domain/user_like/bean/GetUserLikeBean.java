package com.day_walk.backend.domain.user_like.bean;

import com.day_walk.backend.domain.user_like.data.UserLikeEntity;
import com.day_walk.backend.domain.user_like.repository.UserLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetUserLikeBean {

    private final UserLikeRepository userLikeRepository;

    public UserLikeEntity exec(UUID userId) {
        return userLikeRepository.findById(userId).orElse(null);
    }
}
