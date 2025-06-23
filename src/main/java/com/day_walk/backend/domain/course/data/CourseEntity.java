package com.day_walk.backend.domain.course.data;

import com.day_walk.backend.domain.course.data.in.ModifyCourseTitleDto;
import com.day_walk.backend.global.BaseEntity;
import com.day_walk.backend.global.util.StringToUUIDListConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "course")
public class CourseEntity extends BaseEntity {
    @Id
    private UUID id;
    private UUID userId;

    @Lob
    @Convert(converter = StringToUUIDListConverter.class)
    private List<UUID> placeList;

    private String title;
    private boolean visible;
    private boolean hasDelete;

    @Builder
    public CourseEntity(UUID id, UUID userId, String title, boolean visible, List<UUID> placeList, boolean hasDelete) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.visible = visible;
        this.placeList = placeList;
        this.hasDelete = hasDelete;
    }

    public void modifyCourseTitle(ModifyCourseTitleDto dto) {
        title = dto.getTitle();
    }

    public void changeVisible() {
        this.visible = !this.visible;
    }

    public void deleteCourse() {
        this.hasDelete = true;
    }

}
