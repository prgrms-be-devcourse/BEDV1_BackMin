package com.backmin.domains.order.dto.request;

import com.backmin.domains.order.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusParam {

    private Long memberId;

    private String email;

    private String password;

    private OrderStatus orderStatus;

}
