package com.backmin.domains.review.domain;

import com.backmin.domains.common.BaseEntity;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.store.domain.Store;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
public class Review extends BaseEntity {

    @Id
    @Column(name = "review_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "score", nullable = false)
    @Min(1)
    @Max(5)
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "order_id", nullable = false)
    private Long orderId;
}
