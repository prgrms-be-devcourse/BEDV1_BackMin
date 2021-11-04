package com.backmin.domains.review.domain;

import com.backmin.domains.common.BaseEntity;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.order.domain.Order;
import com.backmin.domains.store.domain.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reviews")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "review_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "score")
    private double score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "content", length = 500)
    private String content;

    @Builder
    public Review(Long id, Store store, double score, Member member, Order order, String content) {
        this.id = id;
        this.store = store;
        this.score = score;
        this.member = member;
        this.order = order;
        this.content = content;
    }

    public static Review of(Store store, double score, Member member, Order order, String content) {
        Review review = Review.builder()
                .score(score)
                .content(content)
                .build();

        review.changeStore(store);
        review.changeMember(member);
        review.changeOrder(order);
        return review;
    }

    private void changeOrder(Order order) {
        this.order = order;
    }

    private void changeMember(Member member) {
        this.member = member;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeStore(Store store) {
        this.store = store;
    }

}
