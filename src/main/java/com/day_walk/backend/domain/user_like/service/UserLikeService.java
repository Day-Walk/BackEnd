package com.day_walk.backend.domain.user_like.service;

import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.domain.user_like.bean.SaveUserLikeEntityBean;
import com.day_walk.backend.domain.user_like.bean.GetUserLikeEntityBean;
import com.day_walk.backend.domain.user_like.data.UserLikeEntity;
import com.day_walk.backend.domain.user_like.data.dto.in.SaveUserLikeDto;
import com.day_walk.backend.domain.user_like.data.dto.in.SaveUserTagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserLikeService {

    private final SaveUserLikeEntityBean saveUserLikeEntityBean;
    private final GetUserLikeEntityBean getUserLikeEntityBean;
    private final GetUserEntityBean getUserEntityBean;

    public UUID createUserLike(SaveUserLikeDto saveUserLikeDto) {
        UUID userId = saveUserLikeDto.getUserId();
        for (SaveUserTagDto saveUserTagDto : saveUserLikeDto.getCategoryList()) {
            UserLikeEntity userLikeEntity = new UserLikeEntity(userId, saveUserTagDto);
            saveUserLikeEntityBean.exec(userLikeEntity);
        }

        return userId;
    }

}
