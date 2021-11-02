package com.backmin.domains.review.domain.dto.request;

import com.backmin.domains.member.domain.Member;
import com.backmin.domains.order.domain.Order;
import com.backmin.domains.store.domain.Store;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateParam {

    private Long id;

    private Store store;

    private double score;

    private Member member;

    private Order order;

    private String content;

    public static ReviewCreateParam of(
            Long id,
            Store store,
            double score,
            Member member,
            Order order,
            String content
    ) {
        ReviewCreateParam reviewCreateParam = new ReviewCreateParam();
        reviewCreateParam.setId(id);
        reviewCreateParam.setStore(store);
        reviewCreateParam.setScore(score);
        reviewCreateParam.setMember(member);
        reviewCreateParam.setOrder(order);
        reviewCreateParam.setContent(content);

        return reviewCreateParam;
    }
}
