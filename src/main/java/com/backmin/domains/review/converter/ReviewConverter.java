package com.backmin.domains.review.converter;

import com.backmin.domains.common.dto.PageResult;
import com.backmin.domains.review.domain.Review;
import com.backmin.domains.review.domain.dto.response.ReviewResult;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter {

    public PageResult<ReviewResult> convertReviewToReviewResult(Page<Review> reviews) {
        List<ReviewResult> reviewResultList = reviews.getContent().stream()
                .map(review -> {
                    ReviewResult result = new ReviewResult();
                    result.setId(review.getId());
                    result.setStoreId(review.getStore().getId());
                    result.setMemberId(review.getMember().getId());
                    result.setNickName(review.getMember().getNickName());
                    result.setOrderId(review.getOrder().getId());
                    result.setScore(review.getScore());
                    result.setContent(review.getContent());
                    result.setCreatedAt(review.getCreatedAt());
                    result.setUpdatedAt(review.getUpdatedAt());
                    return result;
                }).collect(Collectors.toList());

        PageResult<ReviewResult> pageResult = new PageResult<>();
        pageResult.setPageNumber(reviews.getNumber());
        pageResult.setPageSize(reviews.getSize());
        pageResult.setHasNext(reviews.hasNext());
        pageResult.setTotalCount(reviews.getNumberOfElements());
        pageResult.setList(reviewResultList);
        return pageResult;
    }
}
