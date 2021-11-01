package com.backmin.domains.store.controller;

import com.backmin.domains.common.dto.ApiResult;
import com.backmin.domains.common.dto.PageDto;
import com.backmin.domains.store.dto.DetailStoreInfoReadResponse;
import com.backmin.domains.store.dto.StoreInfoAtList;
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

    @GetMapping(value = "/categories/{categoryId}/stores",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<PageDto<StoreInfoAtList>> list(@PathVariable("categoryId") Long categoryId, Pageable pageRequest) {
        PageDto<StoreInfoAtList> storeInfoAtListPageDto = storeService.readPagingStoresByCategoryId(categoryId, pageRequest);

        return ApiResult.ok(storeInfoAtListPageDto);
    }

    @GetMapping(value = "/stores/{storeId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<DetailStoreInfoReadResponse> detail(@PathVariable("storeId") Long storeId) {
        DetailStoreInfoReadResponse detailStoreInfoReadResponse = storeService.readDetailStore(storeId);

        return ApiResult.ok(detailStoreInfoReadResponse);
    }

    @GetMapping(value = "/stores",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<PageDto<StoreInfoAtList>> list(@RequestParam("keyword") String storeName, Pageable pageRequest) {
        PageDto<StoreInfoAtList> storeInfoAtListPageDto = storeService.searchStoresByName(storeName, pageRequest);

        return ApiResult.ok(storeInfoAtListPageDto);
    }

}
