package com.backmin.domains.order.domain;

import com.backmin.domains.menu.domain.MenuOption;

import javax.persistence.*;

@Entity
public class OrderMenuOption {

    @Id
    private Long sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_option_id")
    private MenuOption menuOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_menu_id")
    private OrderMenu orderMenu;

}
