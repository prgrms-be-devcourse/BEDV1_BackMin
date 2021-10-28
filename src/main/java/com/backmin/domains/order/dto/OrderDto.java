package com.backmin.domains.order.dto;

import com.backmin.domains.order.domain.OrderStatus;
import com.backmin.domains.order.domain.Payment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

    private Long id;

    private String address;

    private OrderStatus status;

    private String requirement;

    private LocalDateTime requestAt;

    private LocalDateTime completeAt;

    private Payment payMent;

    private int totalPrice;

}
