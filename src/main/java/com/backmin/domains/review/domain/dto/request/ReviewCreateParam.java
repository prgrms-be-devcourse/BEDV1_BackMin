package com.backmin.domains.review.domain.dto.request;

import com.backmin.domains.member.domain.Member;
import com.backmin.domains.order.domain.Order;
import com.backmin.domains.store.domain.Store;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateParam {

    private Long storeId;

    private double score;

    private Long memberId;

    private String nickName;

    private Long orderId;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
