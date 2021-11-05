package com.backmin.domains.order.domain;

import com.backmin.domains.menu.domain.MenuOption;

import java.util.Objects;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_menu_option")
public class OrderMenuOption {

    @Id
    @GeneratedValue
    @Column(name = "order_menu_option_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_option_id", nullable = false)
    private MenuOption menuOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_menu_id", nullable = false)
    private OrderMenu orderMenu;

    private int price;

    @Builder
    public OrderMenuOption(Long id, MenuOption menuOption, int price) {
        this.id = id;
        this.menuOption = menuOption;
        this.price = price;
    }

    public static OrderMenuOption of(MenuOption menuOption, int price) {
        return OrderMenuOption.builder()
                .menuOption(menuOption)
                .price(price)
                .build();
    }

    public void changeMenuOption(MenuOption menuOption) {
        if (Objects.nonNull(this.menuOption)) {
            this.menuOption.getOrderMenuOptions().remove(this);
        } 
      
        this.menuOption = menuOption;
        menuOption.getOrderMenuOptions().add(this);
    }  
      
    public void changeOrderMenu(OrderMenu orderMenu) {
        if (Objects.nonNull(this.orderMenu)) {
            this.orderMenu.getOrderMenuOptions().remove(this);
        }
      
        this.orderMenu = orderMenu;
        orderMenu.getOrderMenuOptions().add(this);
    }

}
