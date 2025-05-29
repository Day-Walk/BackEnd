package com.day_walk.backend.domain.click_log.service;

import com.day_walk.backend.domain.click_log.bean.SaveClickLogBean;
import com.day_walk.backend.domain.click_log.data.ClickLogEntity;
import com.day_walk.backend.domain.click_log.data.in.SaveClickLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClickLogService {
    private final SaveClickLogBean saveClickLogBean;

    public boolean save(SaveClickLogDto saveClickLogDto) {
        ClickLogEntity clickLog = ClickLogEntity.builder()
                .saveClickLogDto(saveClickLogDto)
                .build();

        saveClickLogBean.exec(clickLog);
        return true;
    }
}
