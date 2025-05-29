package com.day_walk.backend.domain.user_like.data.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SaveUserTagDto {
    private String categoryName;
    private List<String> tagList;
}
