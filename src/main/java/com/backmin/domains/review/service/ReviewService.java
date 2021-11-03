package com.backmin.domains.review.service;

import com.backmin.config.exception.BusinessException;
import com.backmin.domains.common.dto.PageResult;
import com.backmin.domains.common.enums.ErrorInfo;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.order.domain.Order;
import com.backmin.domains.order.domain.OrderRepository;
import com.backmin.domains.review.converter.ReviewConverter;
import com.backmin.domains.review.domain.Review;
import com.backmin.domains.review.domain.ReviewRepository;
import com.backmin.domains.review.domain.dto.request.ReviewCreateParam;
import com.backmin.domains.review.domain.dto.response.ReviewResult;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.domain.StoreRepository;
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
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void save(ReviewCreateParam reviewCreateParam) {
        Store store = storeRepository.findById(reviewCreateParam.getStoreId()).orElseThrow(() -> new BusinessException(ErrorInfo.STORE_NOT_FOUND));
        Member member = memberRepository.findById(reviewCreateParam.getMemberId()).orElseThrow(() -> new BusinessException(ErrorInfo.MEMBER_NOT_FOUND));
        Order order = orderRepository.findById(reviewCreateParam.getOrderId()).orElseThrow(() -> new BusinessException(ErrorInfo.ORDER_NOT_FOUND));
        reviewRepository.save(Review.of(store,
                reviewCreateParam.getScore(), member, order, reviewCreateParam.getContent()));
    }

    @Transactional(readOnly = true)
    public PageResult<ReviewResult> getReviewsByStoreId(Long storeId, Pageable pageable) {
        if (storeRepository.existsById(storeId)) {
            Page<Review> reviews = reviewRepository.getAllByStoreId(storeId, pageable);
            return reviewConverter.convertReviewToReviewResult(reviews);
        }
        throw new BusinessException(ErrorInfo.STORE_NOT_FOUND);

    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review foundReview = reviewRepository.findById(reviewId).orElseThrow(() -> new BusinessException(ErrorInfo.REVIEW_NOT_FOUND));
        reviewRepository.delete(foundReview);

    }
}
