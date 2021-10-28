package com.backmin.domains.review.domain;

import com.backmin.domains.common.BaseEntity;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.store.domain.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reviews")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "review_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "score", nullable = true)
    @Min(1)
    @Max(5)
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "content", length = 500)
    private String content;

    @Builder
    public Review(Long id, Store store, int score, Member member, Long orderId, String content) {
        this.id = id;
        this.store = store;
        this.score = score;
        this.member = member;
        this.orderId = orderId;
        this.content = content;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeStore(Store store) {
        if (Objects.nonNull(this.store)) {
            this.store.getReviews().remove(this);
        }

        this.store = store;
        store.getReviews().add(this);
    }

}
