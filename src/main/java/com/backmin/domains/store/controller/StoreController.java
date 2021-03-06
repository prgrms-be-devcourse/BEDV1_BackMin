package com.backmin.domains.store.controller;

import com.backmin.domains.common.dto.ApiResult;
import com.backmin.domains.common.dto.PageResult;
import com.backmin.domains.store.dto.response.DetailStoreReadResult;
import com.backmin.domains.store.dto.response.StoreAtListResult;
import com.backmin.domains.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/api/v1/bm")
public class StoreController {

    private final StoreService storeService;

    @GetMapping(value = "/categories/{categoryId}/stores", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<PageResult<StoreAtListResult>> list(@PathVariable("categoryId") Long categoryId, Pageable pageRequest) {
        return ApiResult.ok(storeService.readPagingStoresByCategoryId(categoryId, pageRequest));
    }

    @GetMapping(value = "/stores/{storeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<DetailStoreReadResult> detail(@PathVariable("storeId") Long storeId) {
        return ApiResult.ok(storeService.readDetailStore(storeId));
    }

    @GetMapping(value = "/stores", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<PageResult<StoreAtListResult>> list(@RequestParam("keyword") String storeName, Pageable pageRequest) {
        return ApiResult.ok(storeService.searchStoresByName(storeName, pageRequest));
    }

}
