package com.day_walk.backend.domain.tag.service;

import com.day_walk.backend.domain.tag.bean.GetTagBean;
import com.day_walk.backend.domain.tag.data.TagEntity;
import com.day_walk.backend.domain.tag.data.out.GetTagByCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagService {
    // TagEntity 가져오는 컴포넌트
    private final GetTagBean getTagBean;

    // 카테고리와 연관되어있는 모든 태그 반환
    public List<GetTagByCategoryDto> getTagByCategory(UUID categoryId) {
        List<TagEntity> tagEntityList = getTagBean.exec(categoryId);

        return tagEntityList.stream()
                .map(tag -> GetTagByCategoryDto.builder().tag(tag).build())
                .collect(Collectors.toList());
    }
}
