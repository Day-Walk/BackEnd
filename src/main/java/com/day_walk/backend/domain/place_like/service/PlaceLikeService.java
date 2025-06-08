package com.day_walk.backend.domain.place_like.service;

import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.data.out.GetPlaceByLikeDto;
import com.day_walk.backend.domain.place_like.bean.GetPlaceLikeEntityBean;
import com.day_walk.backend.domain.place_like.data.PlaceLikeEntity;
import com.day_walk.backend.domain.place_like.data.in.PlaceLikeDto;
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

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PlaceLikeService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final KafkaTemplate<String, PlaceLikeDto> kafkaTemplate;

    private final GetUserEntityBean getUserEntityBean;
    private final GetPlaceLikeEntityBean getPlaceLikeEntityBean;
    private final GetPlaceEntityBean getPlaceEntityBean;

    public boolean savePlaceLike(PlaceLikeDto savePlaceLikeDto) {
        UserEntity user = getUserEntityBean.exec(savePlaceLikeDto.getUserId());
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        PlaceEntity place = getPlaceEntityBean.exec(savePlaceLikeDto.getPlaceId());
        if (place == null) {
            throw new CustomException(ErrorCode.PLACE_NOT_FOUND);
        }

        kafkaTemplate.send("save-place-like", new PlaceLikeDto(savePlaceLikeDto.getUserId(), savePlaceLikeDto.getPlaceId()));

        return true;
    }

    public boolean deletePlaceLike(PlaceLikeDto deletePlaceDto) {
        kafkaTemplate.send("delete-place-like", new PlaceLikeDto(deletePlaceDto.getUserId(), deletePlaceDto.getPlaceId()));

        return true;
    }

    public List<PageDto<GetPlaceByLikeDto>> getPlaceLike(UUID userId) {
        UserEntity user = getUserEntityBean.exec(userId);
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        List<PlaceLikeEntity> placeLikeList = getPlaceLikeEntityBean.exec(userId);
        if (placeLikeList == null || placeLikeList.isEmpty()) {
            return Collections.emptyList();
        }

        List<GetPlaceByLikeDto> placeByLikeDtoList = placeLikeList.stream()
                .map(placeLike -> GetPlaceByLikeDto.builder()
                        .place(getPlaceEntityBean.exec(placeLike.getPlaceId()))
                        .build())
                .toList();

        return PaginationUtil.paginate(placeByLikeDtoList, 10);
    }

    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    public void flushLikesToKafka() {
        Set<String> keys = redisTemplate.keys("*:*");
        if (keys != null) {
            for (String key : keys) {
                Boolean liked = (Boolean) redisTemplate.opsForValue().get(key);
                if (liked != null && liked) {
                    String[] parts = key.split(":");
                    kafkaTemplate.send("bulk-place-like", new PlaceLikeDto(UUID.fromString(parts[1]), UUID.fromString(parts[2])));
                }
            }
        }
    }
}
