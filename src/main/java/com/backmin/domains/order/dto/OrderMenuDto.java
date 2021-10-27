package com.backmin.domains.order.dto;

import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.order.domain.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMenuDto {

    private Long orderMenuId;

    private Menu menu;

    private Order order;

    private int quantity;

    private int price;
}
