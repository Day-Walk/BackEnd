package com.day_walk.backend.domain.chat.data.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetChatByMlDto {
    private List<UUID> placeid;
    private String str;
}
