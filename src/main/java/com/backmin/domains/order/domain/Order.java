package com.backmin.domains.order.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Order {

    @Id
    private Long id;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String requirement;

    private LocalDateTime requestAt;

    @Enumerated(EnumType.STRING)
    private Payment payMent;

}
