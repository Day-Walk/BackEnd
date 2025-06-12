package com.day_walk.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, false, "USER_010", "유저를 찾을 수 없습니다."),
//    USER_AGE_BAD_REQUEST(HttpStatus.BAD_REQUEST, false, "USER_020", "유저의 나이 입력이 잘못되었습니다."),
    USER_SAVE_FAILED(HttpStatus.BAD_REQUEST, false, "USER_020", "유저의 정보를 저장할 수 없습니다."),
    USER_AGE_GENDER_SAVE_FAILED(HttpStatus.BAD_REQUEST, false, "USER_030", "유저의 나이와 성별 정보가 이미 존재합니다."),

    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, false, "PLACE_010", "장소를 찾을 수 없습니다."),
    PLACE_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, false, "PLACE_011", "이미지가 존재하지 않습니다."),

    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, false, "TAG_010", "잘못된 태그가 존재합니다."),

    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, false, "REVIEW_010", "리뷰를 찾을 수 없습니다."),

    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, false, "CATEGORY_010", "카테고리를 찾을 수 없습니다."),

    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, false, "COURSE_010", "코스를 찾을 수 없습니다."),
    COURSE_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, false, "COURSE_011", "코스리스트를 찾을 수 없습니다."),
    COURSE_DELETE_TRUE(HttpStatus.BAD_REQUEST, false, "COURSE_030", "이미 삭제된 코스입니다."),
    COURSE_VISIBLE_FALSE(HttpStatus.BAD_REQUEST, false, "COURSE_031", "비공개 처리된 코스입니다."),

    COURSE_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, false, "COURSE_LIKE_010", "코스 찜한 내역을 찾을 수 없습니다."),
    COURSE_LIKE_IS_EXIST(HttpStatus.BAD_REQUEST, false, "COURSE_LIKE_030", "이미 찜한 코스입니다."),

    PLACE_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, false, "PLACE_LIKE_010", "장소 찜 내역을 찾을 수 없습니다."),

    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, false, "IMAGE_010", "이미지를 불러올 수 없습니다."),
    IMAGE_DELETE_FAIL(HttpStatus.BAD_REQUEST, false, "IMAGE_040", "이미지 삭제를 실패했습니다.");

    private final HttpStatus httpStatus;
    private final boolean success;
    private final String code;
    private final String message;
}
