package com.backmin.domains.menu.domain;

import com.backmin.domains.store.domain.Store;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu_category")
public class MenuCategory {

    @Id
    @GeneratedValue
    @Column(name = "menu_category_id")
    private Long id;

    @Column(name = "menu_category_name")
    private String name;

    @OneToMany(mappedBy = "menuCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menuList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}
