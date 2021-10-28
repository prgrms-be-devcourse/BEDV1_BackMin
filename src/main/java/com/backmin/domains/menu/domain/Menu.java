package com.backmin.domains.menu.domain;

import com.backmin.domains.common.BaseEntity;
import com.backmin.domains.order.domain.OrderMenu;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(name = "decription", length = 200, nullable = false)
    private String description;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuOption> menuOptions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_category_id")
    private MenuCategory menuCategory;

    @Builder
    public Menu(Long id,
                String name,
                boolean isBest,
                boolean isSoldOut,
                boolean isPopular,
                int price,
                String description,
                MenuCategory menuCategory
    ) {
        this.id = id;
        this.name = name;
        this.isBest = isBest;
        this.isSoldOut = isSoldOut;
        this.isPopular = isPopular;
        this.price = price;
        this.description = description;
        this.menuCategory = menuCategory;
        this.orderMenus = new ArrayList<>();
        this.menuOptions = new ArrayList<>();
    }

    public void addMenuOption(MenuOption menuOption) {
        menuOption.changeMenu(this);
    }

    public void addOrderMenu(OrderMenu orderMenu) {
        orderMenu.changeMenu(this);
    }

    public void changeMenuCategory(MenuCategory menuCategory) {
        if (Objects.nonNull(this.menuCategory)) {
            this.menuCategory.getMenus().remove(this);
        }

        this.menuCategory = menuCategory;
        menuCategory.getMenus().add(this);
    }

}
