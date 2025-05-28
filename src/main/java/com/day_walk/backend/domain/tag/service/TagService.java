package com.day_walk.backend.domain.tag.service;

import com.day_walk.backend.domain.tag.bean.GetTagBean;
import com.day_walk.backend.domain.tag.data.TagEntity;
import com.day_walk.backend.domain.tag.data.out.GetTagByCategoryDto;
import com.day_walk.backend.domain.tag.data.out.GetTagByPlaceDto;
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

    // 장소 가져오는 컴포넌트 추가 예정
    // private final GetPlaceBean getPlaceBean;

    // 카테고리와 연관되어있는 모든 태그 반환
    public List<GetTagByCategoryDto> getTagByCategory(UUID categoryId) {
        List<TagEntity> tagEntityList = getTagBean.exec(categoryId);

        return tagEntityList.stream()
                .map(tag -> GetTagByCategoryDto.builder().tag(tag).build())
                .collect(Collectors.toList());
    }

    // 장소와 연관되어있는 모든 태그 반환
    public GetTagByPlaceDto getTagByPlace(UUID placeId) {
        /*
        PlaceEntity place = getPlaceBean.exec(placeId);

        place 관련 validation check 추가

        UUID categoryId = place.getCategoryId();
        */

        List<GetTagByCategoryDto> tagList = this.getTagByCategory(placeId); // categoryId로 변경 예정

        // return GetTagByPlaceDto.Builder().place(place).tagList(tagList).build();
        return null;
    }
}
