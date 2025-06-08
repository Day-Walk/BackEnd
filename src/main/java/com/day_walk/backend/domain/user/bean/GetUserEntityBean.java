package com.day_walk.backend.domain.user.bean;

import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GetUserEntityBean {
    private final UserRepository userRepository;

    public UserEntity exec(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public UserEntity exec(Long kakaoId) {
        return userRepository.findByKakaoId(kakaoId);
    }
}
