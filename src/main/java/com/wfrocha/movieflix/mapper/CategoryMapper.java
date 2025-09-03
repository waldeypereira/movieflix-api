package com.wfrocha.movieflix.mapper;

import com.wfrocha.movieflix.controller.request.CategoryRequest;
import com.wfrocha.movieflix.controller.response.CategoryResponse;
import com.wfrocha.movieflix.entity.Category;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CategoryMapper {

    public static Category toCategory(CategoryRequest request) {
        return Category.builder()
                .name(request.name())
                .build();
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static List<CategoryResponse> toCategoryResponseList(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }
}
