package com.backmin.domains.review.converter;

import com.backmin.domains.review.domain.Review;
import com.backmin.domains.review.dto.ReviewCreateRequest;
import com.backmin.domains.review.dto.ReviewUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter {

    public Review convertCreateDtoToReview(ReviewCreateRequest reviewCreateRequest) {
        return Review.builder()
                .id(reviewCreateRequest.getId())
                .score(reviewCreateRequest.getScore())
                .orderId(reviewCreateRequest.getOrderId())
                .content(reviewCreateRequest.getContent())
                .build();
    }

    public Review convertUpdateDtoToReview(ReviewUpdateRequest reviewUpdateRequest) {
        return Review.builder()
                .id(reviewUpdateRequest.getId())
                .score(reviewUpdateRequest.getScore())
                .orderId(reviewUpdateRequest.getOrderId())
                .content(reviewUpdateRequest.getContent())
                .build();
    }

    public ReviewCreateRequest convertReviewToDto(Review review) {
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest();
        reviewCreateRequest.setId(review.getId());
        reviewCreateRequest.setScore(review.getScore());
        reviewCreateRequest.setOrderId(review.getOrderId());
        reviewCreateRequest.setContent(review.getContent());
        return reviewCreateRequest;
    }

}
