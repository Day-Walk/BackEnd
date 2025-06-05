package com.day_walk.backend.domain.tag.bean;

import com.day_walk.backend.domain.tag.data.TagEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetTagNameBean {
    public List<String> exec(List<TagEntity> tagEntities) {
        return tagEntities.stream()
                .map(TagEntity::getFullName)
                .collect(Collectors.toList());
    }
}
