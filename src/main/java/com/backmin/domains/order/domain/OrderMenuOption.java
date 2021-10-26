package com.backmin.domains.order.domain;

import com.backmin.domains.menu.domain.MenuOption;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class OrderMenuOption {

    @Id
    @GeneratedValue
    @Column(name = "order_menu_opt_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_option_id")
    private MenuOption menuOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_menu_id")
    private OrderMenu orderMenu;

    public void changeMenuOption(MenuOption menuOption) {
        if (Objects.nonNull(this.orderMenu)) {
            this.orderMenu.getOrderMenuOptions().remove(this);
        }

        this.menuOption = menuOption;
        menuOption.getOrderMenuOptions().add(this);
    }

}
