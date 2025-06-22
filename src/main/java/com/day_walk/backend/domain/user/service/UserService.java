package com.day_walk.backend.domain.user.service;

import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.bean.SaveUserBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.domain.user.data.UserRole;
import com.day_walk.backend.domain.user.data.dto.in.SaveUserDto;
import com.day_walk.backend.domain.user.data.dto.in.SignInUserDto;
import com.day_walk.backend.domain.user.data.dto.in.UpdateUserDto;
import com.day_walk.backend.domain.user.data.dto.out.GetCrowdLevelDto;
import com.day_walk.backend.domain.user.data.dto.out.GetUserDto;
import com.day_walk.backend.domain.user.data.dto.out.GetUserBySignInDto;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final GetUserEntityBean getUserEntityBean;
    private final SaveUserBean saveUserBean;
    private final RestTemplate restTemplate;

    @Value("${ml-server-uri}")
    private String ML_SERVER_URI;

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

    public GetCrowdLevelDto getCrowdLevel(int hour) {
        String uri = UriComponentsBuilder
                .fromUriString(ML_SERVER_URI)
                .queryParam("hour", hour)
                .toUriString();
        try {
            ResponseEntity<GetCrowdLevelDto> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    GetCrowdLevelDto.class
            );

            log.info("ML 응답 상태 코드: {}", response.getStatusCode());
            log.info("ML 응답 본문: {}", response.getBody());

            if (response.getBody() == null) {
                log.warn("ML 응답 body가 null입니다. 요청 URI: {}", uri);
                throw new CustomException(ErrorCode.ML_SERVER_ERROR);
            }

            return response.getBody();
        }catch (HttpStatusCodeException e) {
            log.error("ML 서버 HTTP 오류 발생: {}", e.getStatusCode());
            log.error("오류 응답 본문: {}", e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("ML 서버 요청 중 예외 발생: ", e);
        }
        return null;

    }
}
