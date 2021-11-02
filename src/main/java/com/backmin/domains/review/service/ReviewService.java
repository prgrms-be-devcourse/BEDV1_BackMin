package com.backmin.domains.review.service;

import com.backmin.config.exception.BusinessException;
import com.backmin.domains.common.dto.PageResult;
import com.backmin.domains.common.enums.ErrorInfo;
import com.backmin.domains.review.converter.ReviewConverter;
import com.backmin.domains.review.domain.Review;
import com.backmin.domains.review.domain.ReviewRepository;
import com.backmin.domains.review.domain.dto.request.ReviewCreateParam;
import com.backmin.domains.review.domain.dto.response.ReviewResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewConverter reviewConverter;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void save(ReviewCreateParam reviewCreateParam) {
        reviewRepository.save(Review.of(reviewCreateParam.getStore(),
                reviewCreateParam.getScore(), reviewCreateParam.getMember(), reviewCreateParam.getOrder(), reviewCreateParam.getContent()));
    }

    @Transactional(readOnly = true)
    public PageResult<ReviewResult> getReviewsByStoreId(Long storeId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.getAllByStoreId(storeId, pageable);
        return reviewConverter.convertReviewToReviewResult(reviews);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review foundReview = reviewRepository.findById(reviewId).orElseThrow(() -> new BusinessException(ErrorInfo.NOT_FOUND));
        reviewRepository.delete(foundReview);

    }
}
