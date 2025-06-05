package com.day_walk.backend.domain.sub_category.bean;

import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.sub_category.data.SubCategoryEntity;
import com.day_walk.backend.domain.sub_category.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GetSubCategoryEntityBean {
    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryEntity exec(UUID subCategoryId) {
        return subCategoryRepository.findById(subCategoryId).orElse(null);
    }

    public SubCategoryEntity exec(PlaceEntity place) {
        return subCategoryRepository.findById(place.getSubCategoryId()).orElse(null);
    }
}
