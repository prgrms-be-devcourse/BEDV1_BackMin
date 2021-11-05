package com.backmin.domains.order.dto.request;

import com.backmin.domains.order.domain.OrderStatus;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private OrderStatus orderStatus;

}
