package com.backmin.domains.store.controller;

import com.backmin.domains.common.dto.ApiResult;
import com.backmin.domains.store.dto.response.CategoryAtListResult;
import com.backmin.domains.store.dto.response.CategoriesReadResult;
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
    public ApiResult<CategoriesReadResult> list() {
        return ApiResult.ok(categoryService.readAllCategories());
    }

}
