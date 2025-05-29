package com.day_walk.backend.domain.user_like.bean;

import com.day_walk.backend.domain.user_like.data.UserLikeEntity;
import com.day_walk.backend.domain.user_like.repository.UserLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserLikeBean {

    private final UserLikeRepository userLikeRepository;

    public void exec(UserLikeEntity userLikeEntity) {
        userLikeRepository.save(userLikeEntity);
    }

}
