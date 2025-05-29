package com.day_walk.backend.domain.tag.bean;

import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.tag.data.TagEntity;
import com.day_walk.backend.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
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
}
