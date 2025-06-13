package com.day_walk.backend.domain.course.service;

import com.day_walk.backend.domain.category.bean.GetCategoryEntityBean;
import com.day_walk.backend.domain.category.data.CategoryEntity;
import com.day_walk.backend.domain.course.bean.*;
import com.day_walk.backend.domain.course.data.CourseEntity;
import com.day_walk.backend.domain.course.data.dto.in.ChangeBooleanDto;
import com.day_walk.backend.domain.course.data.dto.in.ModifyCourseTitleDto;
import com.day_walk.backend.domain.course.data.dto.in.SaveCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetAllCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetSearchCourseDto;
import com.day_walk.backend.domain.course.data.dto.out.GetUsersAllCourseDto;
import com.day_walk.backend.domain.course_like.bean.GetCourseLikeEntityBean;
import com.day_walk.backend.domain.course_like.data.CourseLikeEntity;
import com.day_walk.backend.domain.course_like.data.in.CourseLikeDto;
import com.day_walk.backend.domain.place.bean.GetPlaceEntityBean;
import com.day_walk.backend.domain.place.data.PlaceEntity;
import com.day_walk.backend.domain.place.data.out.GetPlaceByCourseDto;
import com.day_walk.backend.domain.place.data.out.GetPlaceWithStarDto;
import com.day_walk.backend.domain.review.bean.GetReviewEntityBean;
import com.day_walk.backend.domain.review.bean.GetReviewStarsAvgBean;
import com.day_walk.backend.domain.review.data.ReviewEntity;
import com.day_walk.backend.domain.sub_category.bean.GetSubCategoryEntityBean;
import com.day_walk.backend.domain.sub_category.data.SubCategoryEntity;
import com.day_walk.backend.domain.user.bean.GetUserEntityBean;
import com.day_walk.backend.domain.user.data.UserEntity;
import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import com.day_walk.backend.global.util.page.PageDto;
import com.day_walk.backend.global.util.page.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final SaveCourseEntityBean saveCourseEntityBean;
    private final GetUserEntityBean getUserEntityBean;
    private final GetCourseEntityBean getCourseEntityBean;
    private final GetPlaceEntityBean getPlaceEntityBean;
    private final GetSubCategoryEntityBean getSubCategoryEntityBean;
    private final GetCategoryEntityBean getCategoryEntityBean;
    private final GetReviewEntityBean getReviewEntityBean;
    private final GetReviewStarsAvgBean getReviewStarsAvgBean;
    private final GetCourseLikeEntityBean getCourseLikeEntityBean;
    private final GetAllCourseEntityBean getAllCourseEntityBean;
    private final GetUsersAllCourseEntityBean getUsersAllCourseEntityBean;
    private final GetSearchCourseEntityBean getSearchCourseEntityBean;

    public UUID saveCourse(SaveCourseDto saveCourseDto) {
        UserEntity userEntity = getUserEntityBean.exec(saveCourseDto.getUserId());
        if (userEntity == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        CourseEntity courseEntity = CourseEntity.builder()
                .id(UUID.randomUUID())
                .userId(saveCourseDto.getUserId())
                .title(saveCourseDto.getTitle())
                .visible(saveCourseDto.isVisible())
                .placeList(saveCourseDto.getPlaceList())
                .hasDelete(false)
                .build();
        saveCourseEntityBean.exec(courseEntity);
        CourseEntity course = saveCourseEntityBean.exec(courseEntity.getId());
        return course == null ? null : course.getId();
    }

    public UUID modifyCourseName(ModifyCourseTitleDto modifyCourseTitleDto) {
        CourseEntity courseEntity = getCourseEntityBean.exec(modifyCourseTitleDto.getCourseId());
        if (courseEntity == null) {
            throw new CustomException(ErrorCode.COURSE_NOT_FOUND);
        }
        courseEntity.modifyCourseTitle(modifyCourseTitleDto);
        saveCourseEntityBean.exec(courseEntity);
        CourseEntity courseId = saveCourseEntityBean.exec(courseEntity.getId());
        return courseId == null ? null : courseId.getId();
    }

    public UUID changeVisible(ChangeBooleanDto changeBooleanDto) {
        CourseEntity courseEntity = getCourseEntityBean.exec(changeBooleanDto.getCourseId());
        if (courseEntity == null) {
            throw new CustomException(ErrorCode.COURSE_NOT_FOUND);
        }
        ;
        courseEntity.changeVisible();
        saveCourseEntityBean.exec(courseEntity);
        CourseEntity courseId = saveCourseEntityBean.exec(courseEntity.getId());
        return courseId == null ? null : courseId.getId();
    }

    public UUID deleteCourse(ChangeBooleanDto changeBooleanDto) {
        CourseEntity courseEntity = getCourseEntityBean.exec(changeBooleanDto.getCourseId());
        if (courseEntity == null) {
            throw new CustomException(ErrorCode.COURSE_NOT_FOUND);
        } else if (courseEntity.isHasDelete()) {
            throw new CustomException(ErrorCode.COURSE_DELETE_TRUE);
        }
        courseEntity.deleteCourse();
        saveCourseEntityBean.exec(courseEntity);
        CourseEntity courseId = saveCourseEntityBean.exec(courseEntity.getId());
        return courseId == null ? null : courseId.getId();
    }

    public GetCourseDto getCourse(UUID courseId, UUID userId) {
        CourseEntity courseEntity = getCourseEntityBean.exec(courseId);
        if (courseEntity == null) {
            throw new CustomException(ErrorCode.COURSE_NOT_FOUND);
        } else if (courseEntity.isHasDelete()) {
            throw new CustomException(ErrorCode.COURSE_DELETE_TRUE);
        } else if (!courseEntity.isVisible() && !courseEntity.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.COURSE_VISIBLE_FALSE);
        }

        UserEntity userEntity = getUserEntityBean.exec(userId);
        if (userEntity == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        List<GetPlaceWithStarDto> getPlaceDtoList = courseEntity.getPlaceList().stream()
                .map(placeId -> {
                    PlaceEntity placeEntity = getPlaceEntityBean.exec(placeId);
                    List<String> imgUrlList = placeEntity.getImgList();
                    String firstImgUrl;
                    if (imgUrlList != null && !imgUrlList.isEmpty()) {
                        firstImgUrl = imgUrlList.get(0);
                    } else {
                        firstImgUrl = null;
                    }

                    SubCategoryEntity subCategory = getSubCategoryEntityBean.exec(placeEntity.getSubCategoryId());
                    CategoryEntity category = getCategoryEntityBean.exec(subCategory.getCategoryId());
                    if (category == null) {
                        throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
                    }

                    List<ReviewEntity> reviewList = getReviewEntityBean.exec(placeEntity);
                    if (reviewList == null) {
                        throw new CustomException(ErrorCode.REVIEW_NOT_FOUND);
                    }
                    double stars = reviewList.isEmpty() ? 0.0 : getReviewStarsAvgBean.exec(reviewList);

                    return GetPlaceWithStarDto.builder()
                            .place(placeEntity)
                            .imgUrl(firstImgUrl)
                            .category(category.getName())
                            .subCategory(subCategory.getName())
                            .stars(stars)
                            .build();
                })
                .collect(Collectors.toList());

        boolean like = false;
        CourseLikeEntity likeEntity = getCourseLikeEntityBean.exec(new CourseLikeDto(userEntity.getId(), courseEntity.getId()));
        like = (likeEntity != null);

        return GetCourseDto.builder()
                .userName(userEntity.getName())
                .title(courseEntity.getTitle())
                .placeList(getPlaceDtoList)
                .like(like)
                .courseLike(getCourseLikeEntityBean.exec(courseEntity))
                .build();
    }

    public List<PageDto<GetAllCourseDto>> getAllCourse(String sortStr, UUID userId) {
        List<CourseEntity> courseEntityList = getAllCourseEntityBean.exec(sortStr);
        if (courseEntityList == null) {
            throw new CustomException(ErrorCode.COURSE_LIST_NOT_FOUND);
        } else if (courseEntityList.isEmpty()) {
            return Collections.emptyList();
        }

        UserEntity userEntity = getUserEntityBean.exec(userId);
        if (userEntity == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        List<GetAllCourseDto> allCourseDtoList = courseEntityList.stream()
                .filter(courseEntity -> courseEntity.isVisible() && !courseEntity.isHasDelete())
                .map(courseEntity -> {
                    boolean liked = getCourseLikeEntityBean.exec(
                            new CourseLikeDto(userId, courseEntity.getId())
                    ) != null;

                    List<GetPlaceByCourseDto> placeList = courseEntity.getPlaceList().stream()
                            .map(placeId -> {
                                PlaceEntity placeEntity = getPlaceEntityBean.exec(placeId);
//                                if (placeEntity == null) return null;
                                return GetPlaceByCourseDto.builder()
                                        .place(placeEntity)
                                        .build();
                            })
                            .collect(Collectors.toList());

                    return GetAllCourseDto.builder()
                            .courseId(courseEntity.getId())
                            .title(courseEntity.getTitle())
                            .userName(getUserEntityBean.exec(courseEntity.getUserId()).getName())
                            .placeList(placeList)
                            .like(liked)
                            .courseLike(getCourseLikeEntityBean.exec(courseEntity))
                            .build();
                })
                .toList();

        return PaginationUtil.paginate(allCourseDtoList, 10);
    }

    public List<PageDto<GetUsersAllCourseDto>> getUsersAllCourse(UUID userId) {
        List<CourseEntity> courseEntityList = getUsersAllCourseEntityBean.exec(userId);
        if (courseEntityList == null) {
            throw new CustomException(ErrorCode.COURSE_LIST_NOT_FOUND);
        } else if (courseEntityList.isEmpty()) {
            return Collections.emptyList();
        }

        List<GetUsersAllCourseDto> usersAllCourseDtoList = courseEntityList.stream()
                .filter(courseEntity -> !courseEntity.isHasDelete())
                .map(courseEntity -> {
                    List<GetPlaceByCourseDto> placeList = courseEntity.getPlaceList().stream()
                            .map(placeId -> {
                                PlaceEntity placeEntity = getPlaceEntityBean.exec(placeId);
//                                if (placeEntity == null) return null;
                                return GetPlaceByCourseDto.builder()
                                        .place(placeEntity)
                                        .build();
                            })
                            .collect(Collectors.toList());

                    return GetUsersAllCourseDto.builder()
                            .courseId(courseEntity.getId())
                            .title(courseEntity.getTitle())
                            .visible(courseEntity.isVisible())
                            .placeList(placeList)
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return PaginationUtil.paginate(usersAllCourseDtoList, 10);
    }

    public List<PageDto<GetSearchCourseDto>> getSearchCourse(String searchStr, String sortStr, UUID userId) {
        List<CourseEntity> courseEntityList = getSearchCourseEntityBean.exec(searchStr, sortStr);
        if (courseEntityList == null) {
            throw new CustomException(ErrorCode.COURSE_LIST_NOT_FOUND);
        } else if (courseEntityList.isEmpty()) {
            Collections.emptyList();
        }

        UserEntity userEntity = getUserEntityBean.exec(userId);
        if (userEntity == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        List<GetSearchCourseDto> courseDtoList = courseEntityList.stream()
                .filter(courseEntity -> courseEntity.isVisible() && !courseEntity.isHasDelete())
                .map(courseEntity -> {
                    boolean liked = getCourseLikeEntityBean.exec(
                            new CourseLikeDto(userId, courseEntity.getId())
                    ) != null;

                    List<GetPlaceByCourseDto> placeList = courseEntity.getPlaceList().stream()
                            .map(placeId -> {
                                PlaceEntity placeEntity = getPlaceEntityBean.exec(placeId);
//                                if (placeEntity == null) return null;
                                return GetPlaceByCourseDto.builder()
                                        .place(placeEntity)
                                        .build();
                            })
                            .collect(Collectors.toList());

                    return GetSearchCourseDto.builder()
                            .courseId(courseEntity.getId())
                            .title(courseEntity.getTitle())
                            .userName(getUserEntityBean.exec(courseEntity.getUserId()).getName())
                            .placeList(placeList)
                            .like(liked)
                            .courseLike(getCourseLikeEntityBean.exec(courseEntity))
                            .build();
                })
                .filter(Objects::nonNull)
                .toList();

        return PaginationUtil.paginate(courseDtoList, 10);
    }
}
