package com.backmin.domains.order.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderMenu {

    @Id
    private Long sequence;

    private Long menuId;

    private Long orderId;

    private Long storeId;

    private int quantity;

    private int price;

}
