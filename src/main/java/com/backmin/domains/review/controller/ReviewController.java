package com.backmin.domains.review.controller;

import com.backmin.domains.common.dto.ApiResult;
import com.backmin.domains.member.dto.MemberUpdateRequest;
import com.backmin.domains.review.dto.ReviewCreateRequest;
import com.backmin.domains.review.dto.ReviewUpdateRequest;
import com.backmin.domains.review.service.ReviewService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
@RequestMapping("api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<Page<ReviewCreateRequest>> reviews(Pageable pageable) {
        return ApiResult.ok(reviewService.findAll(pageable));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<Long> createReview(@RequestBody @Valid ReviewCreateRequest reviewCreateRequest) {
        final Long createdReviewId = reviewService.save(reviewCreateRequest);
        return ApiResult.ok(createdReviewId);
    }

    // 수정 및 추가 구현 필요
    @PutMapping(value = "{/id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<Long> updateReview(@PathVariable("id") Long reviewId, @RequestBody @Valid ReviewUpdateRequest reviewUpdateRequest) {
        final Long updatedReviewId = reviewService.update(reviewId, reviewUpdateRequest);
        return ApiResult.ok(updatedReviewId);
    }

}
