package com.backmin.domains.menu.domain;

import com.backmin.domains.order.domain.OrderMenuOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MenuOption {

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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "menuOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenuOption> orderMenuOptions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

}