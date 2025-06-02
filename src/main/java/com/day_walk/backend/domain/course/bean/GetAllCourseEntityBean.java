package com.day_walk.backend.domain.course.bean;

import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GetAllCourseEntityBean {
    private final CourseRepository courseRepository;

    public List<CourseEntity> exec(String sortStr) {

        // 이거는 ,, 그냥 다 가져온 다음에 보내주기 전에 service에서 정렬해서 보내면 될랑가...
        // courseLike = "like" => 추후 추가 예정 (코스 찜한 사람 많은 순서)
        // createAt = "latest"

        if(sortStr.equals("latest")) {
            return courseRepository.findAll(Sort.by(Sort.Direction.DESC, "createAt"));
        }
        return courseRepository.findAll(Sort.by(Sort.Direction.DESC, "courseLike"));
    }

}
