package com.backmin.domains.menu.domain;

import com.backmin.domains.common.BaseEntity;
import com.backmin.domains.order.domain.OrderMenu;
import com.backmin.domains.store.domain.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menu")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "menu_id", nullable = false)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "is_best")
    private boolean isBest;

    @Column(name = "is_sold_out")
    private boolean isSoldOut;

    @Column(name = "is_popular")
    private boolean isPopular;

    @Column(name = "menu_price")
    private int price;

    @Column(name = "description", length = 200)
    private String description;

    @OneToMany(mappedBy = "menu")
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuOption> menuOptions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public Menu(Long id, String name, boolean isBest, boolean isSoldOut, boolean isPopular, int price, String description) {
        this.id = id;
        this.name = name;
        this.isBest = isBest;
        this.isSoldOut = isSoldOut;
        this.isPopular = isPopular;
        this.price = price;
        this.description = description;
        this.orderMenus = new ArrayList<>();
        this.menuOptions = new ArrayList<>();
    }

    public static Menu of(String name, boolean isBest, boolean isSoldOut, boolean isPopular, int price, String description) {
        Menu menu = Menu.builder()
                .name(name)
                .isBest(isBest)
                .isSoldOut(isSoldOut)
                .isPopular(isPopular)
                .price(price)
                .description(description)
                .build();
        return menu;
    }

    public static Menu of(String name, boolean isBest, boolean isSoldOut, boolean isPopular, int price, String description, List<MenuOption> menuOptions) {
        Menu menu = Menu.builder()
                .name(name)
                .isBest(isBest)
                .isSoldOut(isSoldOut)
                .isPopular(isPopular)
                .price(price)
                .description(description)
                .build();

        menuOptions.forEach(menu::addMenuOption);
        return menu;
    }

    public void addMenuOption(MenuOption menuOption) {
        menuOption.changeMenu(this);
    }

    public void addOrderMenu(OrderMenu orderMenu) {
        orderMenu.changeMenu(this);
    }

    public void changeStore(Store store) {
        if (Objects.nonNull(this.store)) {
            this.store.getMenus().remove(this);
        }

        this.store = store;
        store.getMenus().add(this);
    }

}
