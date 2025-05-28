package com.day_walk.backend.domain.user.data.dto.out;

import com.day_walk.backend.domain.user.data.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;

import java.util.UUID;

@Data
@Builder
public class GetUserDto {
    private UUID id;
    private String name;

}
