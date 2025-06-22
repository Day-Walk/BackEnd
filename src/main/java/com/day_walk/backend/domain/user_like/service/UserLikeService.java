package com.day_walk.backend.domain.user_like.service;

import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.domain.user_like.bean.SaveUserLikeEntityBean;
import com.day_walk.backend.domain.user_like.bean.GetUserLikeEntityBean;
import com.day_walk.backend.domain.user_like.data.UserLikeEntity;
import com.day_walk.backend.domain.user_like.data.in.SaveUserLikeDto;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
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

        UserEntity userEntity = getUserEntityBean.exec(saveUserLikeDto.getUserId());
        if (userEntity == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }


        List<UserLikeEntity> userLikeEntityList = saveUserLikeDto.getCategoryList().stream()
                .map(saveCategoryListDto -> UserLikeEntity.builder()
                        .id(UUID.randomUUID())
                        .userId(userEntity.getId())
                        .categoryId(saveCategoryListDto.getCategoryId())
                        .tagList(saveCategoryListDto.getTagList())
                        .build())
                .toList();

        saveUserLikeEntityBean.exec(userLikeEntityList);

        return userEntity.getId() == null ? null : userEntity.getId();
    }

}
