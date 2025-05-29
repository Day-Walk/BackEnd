package com.day_walk.backend.domain.user_like.service;

import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.domain.user_like.bean.SaveUserLikeEntityBean;
import com.day_walk.backend.domain.user_like.bean.GetUserLikeEntityBean;
import com.day_walk.backend.domain.user_like.data.UserLikeEntity;
import com.day_walk.backend.domain.user_like.data.dto.in.SaveUserLikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserLikeService {

    private final SaveUserLikeEntityBean saveUserLikeEntityBean;
    private final GetUserLikeEntityBean getUserLikeEntityBean;
    private final GetUserEntityBean getUserEntityBean;

    public UUID createUserLike(SaveUserLikeDto saveUserLikeDto) {

        UserEntity userEntity = getUserEntityBean.exec(saveUserLikeDto.getUserId());

        if (userEntity == null) return null;

        UserLikeEntity userLikeEntity = UserLikeEntity.builder().id(UUID.randomUUID()).userId(saveUserLikeDto.getUserId()).build();
        saveUserLikeEntityBean.exec(userLikeEntity);

        UserLikeEntity userId = getUserLikeEntityBean.exec(userLikeEntity.getId());

        return userId == null ? null : userId.getUserId();
    }

}
