package com.day_walk.backend.domain.user.data;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("USER");

    private String value;

    UserRole(String value) {
        this.value = value;
    }

}