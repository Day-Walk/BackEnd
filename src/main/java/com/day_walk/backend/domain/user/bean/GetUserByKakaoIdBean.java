package com.day_walk.backend.domain.user.bean;

import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetUserByKakaoIdBean {

    private final UserRepository userRepository;

    public UserEntity exec(Long kakaoId) {
        return userRepository.findByKakaoId(kakaoId);
    }
}
