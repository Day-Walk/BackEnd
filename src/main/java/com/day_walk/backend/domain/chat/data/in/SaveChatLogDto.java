package com.day_walk.backend.domain.chat.data.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SaveChatLogDto {
    private boolean isSuccess;
    private String message;
}
