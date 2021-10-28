package com.backmin.domains.review.service;

import com.backmin.domains.review.converter.ReviewConverter;
import com.backmin.domains.review.domain.Review;
import com.backmin.domains.review.domain.ReviewRepository;
import com.backmin.domains.review.dto.ReviewCreateRequest;
import com.backmin.domains.review.dto.ReviewUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewConverter reviewConverter;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Long save(ReviewCreateRequest reviewCreateRequest) {
        return reviewRepository.save(reviewConverter.convertCreateDtoToReview(reviewCreateRequest)).getId();
    }

    @Transactional
    public Long update(Long reviewId, ReviewUpdateRequest reviewUpdateRequest) {
        return reviewRepository.findById(reviewUpdateRequest.getId())
                .map(review -> reviewConverter.convertUpdateDtoToReview(reviewUpdateRequest))
                .orElseThrow(() -> new RuntimeException("Not Found Review : " + reviewUpdateRequest.getId())).getId();
    }

    @Transactional
    public Page<ReviewCreateRequest> findAll(Pageable pageable) {
        PageRequest.of(10,10);
        return reviewRepository.findAll(pageable).map(reviewConverter::convertReviewToDto);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review deleteReview = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Not Found Review : " + reviewId));
    }

}
