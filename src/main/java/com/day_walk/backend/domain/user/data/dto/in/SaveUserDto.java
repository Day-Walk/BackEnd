package com.day_walk.backend.domain.user.data.dto.in;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class SaveUserDto {
    private String name;
    private int gender;
    private int age;
}
