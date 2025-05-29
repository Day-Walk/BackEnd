package com.day_walk.backend.domain.tag.bean;

import com.day_walk.backend.domain.tag.data.TagEntity;
import com.day_walk.backend.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class GetTagNameListBean {
    private final TagRepository tagRepository;

    public List<String> exec(List<UUID> tagIdList) {
        if (tagIdList == null || tagIdList.isEmpty()) {
            return Collections.emptyList();
        }

        return tagIdList.stream()
                .map(id -> {
                    TagEntity tag = tagRepository.findById(UUID.fromString(String.valueOf(id))).orElse(null);
                    return tag != null ? tag.getFullName() : null;
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
