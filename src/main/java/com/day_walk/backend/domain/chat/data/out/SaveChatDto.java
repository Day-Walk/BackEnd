package com.day_walk.backend.domain.chat.data.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SaveChatDto {
    private UUID userId;
    private String question;
    private GetChatDto answer;
    private String createAt;
}
