package com.day_walk.backend.global.util.page;

import java.util.ArrayList;
import java.util.List;

public class PaginationUtil {
    public static <T> List<PageDto<T>> paginate(List<T> list, int pageSize) {
        List<PageDto<T>> result = new ArrayList<>();

        for (int i = 0; i < list.size(); i += pageSize) {
            List<T> subList = list.subList(i, Math.min(i + pageSize, list.size()));
            result.add(new PageDto<>(subList));
        }

        return result;
    }
}
