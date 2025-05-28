package com.day_walk.backend.domain.category.service;

import com.day_walk.backend.domain.category.bean.GetCategoryEntityBean;
import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.category.data.dto.out.GetCategoryDto;
import com.day_walk.backend.domain.tag.bean.GetTagBean;
import com.day_walk.backend.domain.tag.data.out.GetTagByCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final GetCategoryEntityBean getCategoryEntityBean;
    private final GetTagBean getTagBean;

    public List<GetCategoryDto> getAllCategory() {
        List<CategoryEntity> categoryEntityList = getCategoryEntityBean.exec();

        return categoryEntityList.stream()
                .map(category -> GetCategoryDto.builder().category(category).tagList(
                        getTagBean.exec(category.getId()).stream()
                                .map(tag -> GetTagByCategoryDto.builder().tag(tag).build())
                                .toList()
                ).build())
                .collect(Collectors.toList());
    }
}
