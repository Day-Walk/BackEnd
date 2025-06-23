package com.day_walk.backend.domain.user.bean;

import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveUserBean {

    private final UserRepository userRepository;

    public void exec(UserEntity user) {
        userRepository.save(user);
    }
}
