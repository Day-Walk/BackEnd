package com.day_walk.backend.domain.user.data;

import com.day_walk.backend.domain.user.data.dto.in.SaveUserDto;
import com.day_walk.backend.domain.user.data.dto.in.UpdateUserDto;
import com.day_walk.backend.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "user")
@Entity
public class UserEntity extends BaseEntity {
    @Id
    private UUID id;
    private Long kakaoId;
    private String name;
    private int gender;
    private int age;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public void saveUser(SaveUserDto saveUserDto) {
        name = saveUserDto.getName();
        gender = saveUserDto.getGender();
        age = saveUserDto.getAge();
    }

    public void updateUser(UpdateUserDto updateUserDto) {
        name = updateUserDto.getName();
    }

    @Builder
    public UserEntity(Long kakaoId, UUID id, String name, UserRole userRole, int gender, int age) {
        this.kakaoId = kakaoId;
        this.name = name;
        this.id = id;
        this.userRole = userRole;
        this.gender = gender;
        this.age = age;
    }

}
