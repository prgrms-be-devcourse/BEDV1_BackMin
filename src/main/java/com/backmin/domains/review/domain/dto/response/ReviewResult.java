package com.backmin.domains.review.domain.dto.response;

import com.backmin.domains.member.domain.Member;
import com.backmin.domains.order.domain.Order;
import com.backmin.domains.store.domain.Store;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResult {

    private Long id;

    private Long storeId;

    private double score;

    private Long memberId;

    private String nickName;

    private Long orderId;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static ReviewResult of(
            Long id,
            Store store,
            double score,
            Member member,
            Order order,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        ReviewResult reviewResult = new ReviewResult();
        reviewResult.setId(id);
        reviewResult.setStoreId(store.getId());
        reviewResult.setMemberId(member.getId());
        reviewResult.setNickName(member.getNickName());
        reviewResult.setOrderId(order.getId());
        reviewResult.setScore(score);
        reviewResult.setContent(content);
        reviewResult.setCreatedAt(createdAt);
        reviewResult.setUpdatedAt(updatedAt);

        return reviewResult;
    }
}
