package com.day_walk.backend.domain.click_log.bean;

import com.day_walk.backend.domain.click_log.data.ClickLogEntity;
import com.day_walk.backend.domain.click_log.repository.ClickLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SaveClickLogBean {
    private final ClickLogRepository clickLogRepository;

    public void exec(ClickLogEntity clickLog) {
        clickLogRepository.save(clickLog);
    }
}
