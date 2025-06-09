package com.day_walk.backend.domain.course_like.service;

import com.day_walk.backend.domain.course.bean.GetCourseEntityBean;
import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.course.data.dto.out.GetCourseByLikeDto;
import com.day_walk.backend.domain.course_like.bean.GetCourseLikeEntityBean;
import com.day_walk.backend.domain.course_like.data.CourseLikeEntity;
import com.day_walk.backend.domain.course_like.data.in.CourseLikeDto;
import com.day_walk.backend.domain.course_like.data.out.CourseLikeEvent;
import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.out.GetPlaceByCourseDto;
import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import com.day_walk.backend.global.util.page.PageDto;
import com.day_walk.backend.global.util.page.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CourseLikeService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final KafkaTemplate<String, CourseLikeEvent> kafkaTemplate;

    private final GetUserEntityBean getUserEntityBean;
    private final GetCourseEntityBean getCourseEntityBean;
    private final GetCourseLikeEntityBean getCourseLikeEntityBean;
    private final GetPlaceEntityBean getPlaceEntityBean;

    public boolean saveCourseLike(CourseLikeDto saveCourseLikeDto) {
        UserEntity user = getUserEntityBean.exec(saveCourseLikeDto.getUserId());
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        CourseEntity course = getCourseEntityBean.exec(saveCourseLikeDto.getCourseId());
        if (course == null) {
            throw new CustomException(ErrorCode.COURSE_NOT_FOUND);
        }

        kafkaTemplate.send("save-course-like", new CourseLikeEvent(saveCourseLikeDto.getUserId(), saveCourseLikeDto.getCourseId(), true));

        return true;
    }

    public boolean deleteCourseLike(CourseLikeDto deleteCourseLikeDto) {
        kafkaTemplate.send("save-course-like", new CourseLikeEvent(deleteCourseLikeDto.getUserId(), deleteCourseLikeDto.getCourseId(), false));

        return true;
    }

    public List<PageDto<GetCourseByLikeDto>> getCourseLike(UUID userId) {
        UserEntity user = getUserEntityBean.exec(userId);
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        List<CourseLikeEntity> courseLikeList = getCourseLikeEntityBean.exec(userId);
        if (courseLikeList == null || courseLikeList.isEmpty()) {
            return Collections.emptyList();
        }

        List<GetCourseByLikeDto> courseByLikeDtoList = courseLikeList.stream()
                .map(courseLike -> {
                    CourseEntity course = getCourseEntityBean.exec(courseLike.getCourseId());
                    if (!course.isVisible()) {
                        return null;
                    }

                    return GetCourseByLikeDto.builder()
                            .course(getCourseEntityBean.exec(courseLike.getCourseId()))
                            .user(getUserEntityBean.exec(courseLike.getUserId()))
                            .courseLike(getCourseLikeEntityBean.exec(course))
                            .placeList(course.getPlaceList().stream()
                                    .map(placeId -> GetPlaceByCourseDto.builder()
                                            .place(getPlaceEntityBean.exec(placeId))
                                            .build())
                                    .collect(Collectors.toList()))
                            .build();
                })
                .filter(Objects::nonNull)
                .toList();

        return PaginationUtil.paginate(courseByLikeDtoList, 10);
    }

    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    public void flushLikesToKafka() {
        Set<String> keys = redisTemplate.keys("*:*");
        if (keys != null) {
            for (String key : keys) {
                Boolean liked = (Boolean) redisTemplate.opsForValue().get(key);
                if (liked != null) {
                    String[] parts = key.split(":");
                    kafkaTemplate.send("bulk-course-like", new CourseLikeEvent(UUID.fromString(parts[1]), UUID.fromString(parts[2]), liked));
                }
            }
        }
    }
}
