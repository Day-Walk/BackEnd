package com.day_walk.backend.domain.chat.data.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetChatByMlDto {
    private String type;
    private String str1;
    private List<UUID> placeid;
    private String str2;
    private String userid;
    private LocalDateTime timestamp;
}
