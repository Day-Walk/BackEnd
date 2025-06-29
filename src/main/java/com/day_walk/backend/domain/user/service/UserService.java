package com.day_walk.backend.domain.user.service;

import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.bean.SaveUserBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.domain.user.data.UserRole;
import com.day_walk.backend.domain.user.data.in.SaveUserDto;
import com.day_walk.backend.domain.user.data.in.SignInUserDto;
import com.day_walk.backend.domain.user.data.in.UpdateUserDto;
import com.day_walk.backend.domain.user.data.out.GetUserDto;
import com.day_walk.backend.domain.user.data.out.GetUserBySignInDto;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final GetUserEntityBean getUserEntityBean;
    private final SaveUserBean saveUserBean;

    public GetUserDto getUserInfo(UUID userId) {
        UserEntity user = getUserEntityBean.exec(userId);
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return GetUserDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .build();
    }

    public UUID saveUserInfo(SaveUserDto userInfo) {
        UserEntity getUser = getUserEntityBean.exec(userInfo.getId());
        if (getUser == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        if (getUser.getAge() == -1 && getUser.getGender() == -1) {
            try {
                getUser.saveUser(userInfo);
                saveUserBean.exec(getUser);
            } catch (Exception e) {
                throw new CustomException(ErrorCode.USER_SAVE_FAILED);
            }
        } else if (getUser.getAge() != -1 && getUser.getGender() != -1) {
            throw new CustomException(ErrorCode.USER_AGE_GENDER_SAVE_FAILED);
        }
        return getUser.getId();
    }

    public UUID updateUserInfo(UpdateUserDto updateUserDto) {
        UserEntity getUser = getUserEntityBean.exec(updateUserDto.getId());
        if (getUser == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        try {
            getUser.updateUser(updateUserDto);
            saveUserBean.exec(getUser);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.USER_SAVE_FAILED);
        }

        return getUser.getId();
    }

    public GetUserBySignInDto signIn(SignInUserDto signInUserDto) {
        UserEntity getEntityByKakaoId = getUserEntityBean.exec(signInUserDto.getKakaoId());

        if (getEntityByKakaoId == null) {
            UserEntity saveUser = UserEntity.builder()
                    .kakaoId(signInUserDto.getKakaoId())
                    .name(signInUserDto.getName())
                    .id(UUID.randomUUID())
                    .userRole(UserRole.USER)
                    .gender(-1)
                    .age(-1)
                    .build();

            saveUserBean.exec(saveUser);

            return GetUserBySignInDto.builder()
                    .userId(saveUser.getId())
                    .name(saveUser.getName())
                    .nextPage("init")
                    .build();
        }

        if (getEntityByKakaoId.isInit()) {
            return GetUserBySignInDto.builder()
                    .userId(getEntityByKakaoId.getId())
                    .name(getEntityByKakaoId.getName())
                    .nextPage("init")
                    .build();
        }

        return GetUserBySignInDto.builder()
                .userId(getEntityByKakaoId.getId())
                .name(getEntityByKakaoId.getName())
                .nextPage("home")
                .build();
    }

    public UserRole getUserRole(UUID userId) {
        UserEntity user = getUserEntityBean.exec(userId);
        if (user == null) {
            return null;
        }

        return user.getUserRole();
    }
}
