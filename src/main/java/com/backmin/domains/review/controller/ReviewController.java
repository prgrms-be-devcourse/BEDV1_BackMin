package com.backmin.domains.review.controller;

import com.backmin.domains.common.dto.ApiResult;
import com.backmin.domains.common.dto.PageResult;
import com.backmin.domains.review.dto.request.ReviewCreateParam;
import com.backmin.domains.review.dto.response.ReviewResult;
import com.backmin.domains.review.service.ReviewService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/bm/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult createReview(@RequestBody @Valid ReviewCreateParam reviewCreateParam) {
        reviewService.save(reviewCreateParam);
        return ApiResult.ok();
    }

    @GetMapping(value = "/store/{storeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<PageResult<ReviewResult>> readAllReview(@PathVariable("storeId") Long storeId, Pageable pageable) {
        return ApiResult.ok(reviewService.getReviewsByStoreId(storeId, pageable));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult deleteReview(@PathVariable("id") Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ApiResult.ok();
    }

}
