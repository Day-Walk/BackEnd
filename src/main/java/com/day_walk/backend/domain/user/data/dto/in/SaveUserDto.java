package com.day_walk.backend.domain.user.data.dto.in;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class SaveUserDto {

    private UUID id;
    private String name;
    private int gender;
    private int age;

    @Builder
    public SaveUserDto(String name, int gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

}
