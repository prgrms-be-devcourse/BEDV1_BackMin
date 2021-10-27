package com.backmin.domains.review.dto;

import com.backmin.domains.member.domain.Member;
import com.backmin.domains.store.domain.Store;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {

    private Long id;

    private Store store;

    private int score;

    private Member member;

    private Long orderId;

    private String content;

}
