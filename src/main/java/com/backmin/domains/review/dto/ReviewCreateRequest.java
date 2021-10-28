package com.backmin.domains.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequest {

    private Long id;

    private int score;

    private Long orderId;

    private String content;
}
