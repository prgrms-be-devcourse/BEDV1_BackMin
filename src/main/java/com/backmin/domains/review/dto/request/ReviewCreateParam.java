package com.backmin.domains.review.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateParam {

    @NotNull
    private Long storeId;

    private double score;

    @NotNull
    private Long memberId;

    private String nickName;

    @NotNull
    private Long orderId;

    private String content;

}
