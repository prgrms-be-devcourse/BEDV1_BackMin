package com.backmin.domains.order.domain;

import com.backmin.domains.menu.domain.Menu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_menu")
public class OrderMenu {

    @Id
    @GeneratedValue
    @Column(name = "order_menu_id", nullable = false)
    private Long orderMenuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private Long storeId;

    @Min(0)
    private int quantity;

    @Min(0)
    private int price;

    @OneToMany(mappedBy = "orderMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenuOption> orderMenuOptions = new ArrayList<>();
  
    public void addOrderMenuOption(OrderMenuOption orderMenuOption) {
        orderMenuOption.changeOrderMenu(this);
    }

    public void changeMenu(Menu menu) {
        if (Objects.nonNull(this.menu)) {
            this.menu.getOrderMenus().remove(this);
        }

        this.menu = menu;
        menu.getOrderMenus().add(this);
    }

    public void changeOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderMenus().remove(this);
        }

        this.order = order;
        order.getOrderMenus().add(this);
    }

}
