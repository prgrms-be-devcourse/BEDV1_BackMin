package com.backmin.domains.review.converter;

import com.backmin.domains.common.dto.PageResult;
import com.backmin.domains.review.domain.Review;
import com.backmin.domains.review.dto.response.ReviewResult;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter {

    public PageResult<ReviewResult> convertReviewToReviewResult(Page<Review> reviews) {
        List<ReviewResult> reviewResultList = reviews.getContent().stream()
                .map(this::createReviewResult)
                .collect(Collectors.toList());

        return createPageResult(reviews, reviewResultList);
    }

    private ReviewResult createReviewResult(Review review) {
        ReviewResult result = new ReviewResult();
        result.setReviewId(review.getId());
        result.setStoreId(review.getStore().getId());
        result.setMemberId(review.getMember().getId());
        result.setNickName(review.getMember().getNickName());
        result.setOrderId(review.getOrder().getId());
        result.setScore(review.getScore());
        result.setContent(review.getContent());
        result.setCreatedAt(review.getCreatedAt());
        result.setUpdatedAt(review.getUpdatedAt());
        return result;
    }

    private PageResult<ReviewResult> createPageResult(Page<Review> reviews, List<ReviewResult> reviewResultList) {
        PageResult<ReviewResult> pageResult = new PageResult<>();
        pageResult.setPageNumber(reviews.getNumber());
        pageResult.setPageSize(reviews.getSize());
        pageResult.setHasNext(reviews.hasNext());
        pageResult.setTotalCount(reviews.getNumberOfElements());
        pageResult.setList(reviewResultList);
        return pageResult;
    }
}
