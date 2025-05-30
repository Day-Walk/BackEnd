package com.day_walk.backend.domain.tag.bean;

import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.tag.data.TagEntity;
import com.day_walk.backend.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GetTagEntityBean {
    private final TagRepository tagRepository;

    public List<TagEntity> exec(CategoryEntity category) {
        return tagRepository.findAllByCategoryId(category.getId());
    }

    public TagEntity exec(UUID tagId) {
        return tagRepository.findById(tagId).orElse(null);
    }

    public List<TagEntity> exec(List<UUID> idList) {
        if (idList == null || idList.isEmpty()) {
            return Collections.emptyList();
        }

        List<UUID> validIds = idList.stream()
                .filter(Objects::nonNull)
                .toList();

        if (validIds.isEmpty()) {
            return Collections.emptyList();
        }

        return tagRepository.findByIdIn(validIds);
    }
}
