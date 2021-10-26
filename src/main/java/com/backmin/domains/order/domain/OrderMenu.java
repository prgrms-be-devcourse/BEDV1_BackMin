package com.backmin.domains.order.domain;

import com.backmin.domains.menu.domain.Menu;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderMenu {

    @Id
    private Long orderMenuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long storeId;

    private int quantity;

    private int price;

    @OneToMany(mappedBy = "orderMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenuOption> orderMenuOptions = new ArrayList<>();

}
