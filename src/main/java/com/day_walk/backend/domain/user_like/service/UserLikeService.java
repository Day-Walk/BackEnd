package com.day_walk.backend.domain.user_like.service;

import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.domain.user_like.bean.CreateUserLikeBean;
import com.day_walk.backend.domain.user_like.bean.GetUserLikeBean;
import com.day_walk.backend.domain.user_like.data.UserLikeEntity;
import com.day_walk.backend.domain.user_like.data.dto.in.CreateUserLikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserLikeService {

    private final CreateUserLikeBean createUserLikeBean;
    private final GetUserLikeBean getUserLikeBean;
    private final GetUserEntityBean getUserEntityBean;

    public UUID createUserLike(CreateUserLikeDto createUserLikeDto) {

        UserEntity userEntity = getUserEntityBean.exec(createUserLikeDto.getUserId());

        if (userEntity == null) return null;

        UserLikeEntity userLikeEntity = UserLikeEntity.builder()
                .id(UUID.randomUUID())
                .userId(createUserLikeDto.getUserId())
                .build();
        createUserLikeBean.exec(userLikeEntity);

        UserLikeEntity userId = getUserLikeBean.exec(userLikeEntity.getId());

        return userId == null ? null : userId.getUserId();
    }

}
