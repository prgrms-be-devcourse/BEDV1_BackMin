package com.backmin.domains.store.controller;

import com.backmin.domains.common.dto.ApiResult;
import com.backmin.domains.store.dto.CategoryDto;
import com.backmin.domains.store.dto.CategoryInfoAtList;
import com.backmin.domains.store.dto.ReadAllCategoriesResponse;
import com.backmin.domains.store.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bm/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<ReadAllCategoriesResponse> list() {
        List<CategoryInfoAtList> categoryInfoAtLists = categoryService.readAllCategories();

        ReadAllCategoriesResponse response = ReadAllCategoriesResponse.of(categoryInfoAtLists);

        return ApiResult.ok(response);
    }

}
