package com.day_walk.backend.domain.user.data;

import com.day_walk.backend.domain.user.data.dto.in.SaveUserDto;
import com.day_walk.backend.domain.user.data.dto.in.UpdateUserDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name="user")
@Entity
public class UserEntity {
    @Id
    private UUID id;
    private Long kakaoId;
    private String name;
    private int gender;
    private int age;


    public void saveUser(SaveUserDto saveUserDto) {
        name = saveUserDto.getName();
        gender = saveUserDto.getGender();
        age = saveUserDto.getAge();
    }

    public void updateUser(UpdateUserDto updateUserDto) {
        name = updateUserDto.getName();
    }

    @Builder
    public UserEntity(String name) {
        this.id = UUID.randomUUID();
        this.kakaoId = 123456789L;
        this.name = name;
    }
}
