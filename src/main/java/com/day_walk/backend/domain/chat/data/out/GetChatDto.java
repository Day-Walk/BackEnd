package com.day_walk.backend.domain.chat.data.out;

import com.day_walk.backend.domain.place.data.out.GetPlaceByChatDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetChatDto {
    private String title;
    private List<GetPlaceByChatDto> placeList;
    private String detail;
}
