package com.backmin.domains.menu.domain;

import com.backmin.domains.common.BaseEntity;
import com.backmin.domains.order.domain.OrderMenuOption;
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
@Table(name = "menu_option")
public class MenuOption extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "menu_option_id", nullable = false)
    private Long id;

    @Column(name = "menu_option_name", nullable = false)
    private String name;

    private Long topOptionId;

    private int maxOptionCount; // quantity??

    private int minOptionCount;

    private boolean isSoldOut;

    @OneToMany(mappedBy = "menuOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenuOption> orderMenuOptions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Builder
    public MenuOption(Long id,
                      String name,
                      Long topOptionId,
                      int maxOptionCount,
                      int minOptionCount,
                      boolean isSoldOut,
                      List<OrderMenuOption> orderMenuOptions,
                      Menu menu
    ) {
        this.id = id;
        this.name = name;
        this.topOptionId = topOptionId;
        this.maxOptionCount = maxOptionCount;
        this.minOptionCount = minOptionCount;
        this.isSoldOut = isSoldOut;
        this.orderMenuOptions = orderMenuOptions;
        this.menu = menu;
    }

    public void addOrderMenuOption(OrderMenuOption orderMenuOption) {
        orderMenuOption.changeMenuOption(this);
    }

    public void changeMenu(Menu menu) {

        if(Objects.nonNull(this.menu)) {
            this.menu.getMenuOptions().remove(this);
        }

        this.menu = menu;
        menu.getMenuOptions().add(this);

    }

}
