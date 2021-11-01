package com.backmin.domains.order.dto;

import com.backmin.domains.order.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusRequest {

    private Long memberId;

    private String email;

    private String password;

    private OrderStatus orderStatus;

}
