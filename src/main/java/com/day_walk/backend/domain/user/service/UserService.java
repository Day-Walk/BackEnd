package com.day_walk.backend.domain.user.service;

import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.bean.SaveUserBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.domain.user.data.dto.in.SaveUserDto;
import com.day_walk.backend.domain.user.data.dto.in.UpdateUserDto;
import com.day_walk.backend.domain.user.data.dto.out.GetUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final GetUserEntityBean getUserEntityBean;

    public GetUserDto getUserInfo(UUID userId) {
        UserEntity user = getUserEntityBean.exec(userId);
        if (user == null) return null;
        return GetUserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    private final SaveUserBean saveUserBean;

    public UUID saveUserInfo(SaveUserDto userInfo) {
        UserEntity user = new UserEntity(userInfo);
        saveUserBean.exec(user);

        return user.getId();
    }

    public UUID updateUserInfo(UpdateUserDto updateUserDto) {
        UserEntity getUser = getUserEntityBean.exec(updateUserDto.getId());
        if (getUser == null) return null;
        getUser.updateUser(updateUserDto);
        saveUserBean.exec(getUser);
        return getUser.getId();
    }

}
