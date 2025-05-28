package com.day_walk.backend.domain.user.data.dto.in;

import com.day_walk.backend.domain.user.data.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class SaveUserDto {

    private UUID id;
    private Long kakaoId;
    private String name;
    private int gender;
    private int age;
//
//    @Builder
//    public SaveUserDto(UserEntity userEntity) {
//        this.id = userEntity.getId();
//        this.kakaoId = userEntity.getKakaoId();
//        this.name = userEntity.getName();
//        this.gender = userEntity.getGender();
//        this.age = userEntity.getAge();
//    }
}
