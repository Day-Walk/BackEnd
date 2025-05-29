package com.day_walk.backend.domain.category.bean;

import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GetCategoryEntityBean {
    private final CategoryRepository categoryRepository;

    public List<CategoryEntity> exec() {
        return categoryRepository.findAll();
    }

    public CategoryEntity exec(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }
}
