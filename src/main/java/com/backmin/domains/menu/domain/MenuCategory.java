package com.backmin.domains.menu.domain;

import com.backmin.domains.common.BaseEntity;
import com.backmin.domains.store.domain.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menu_category")
public class MenuCategory extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "menu_category_id")
    private Long id;

    @Column(name = "menu_category_name")
    private String name;

    @OneToMany(mappedBy = "menuCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public MenuCategory(Long id, String name, List<Menu> menus, Store store) {
        this.id = id;
        this.name = name;
        this.menus = menus;
        this.store = store;
    }

    public void addMenu(Menu menu) {
        menu.changeMenuCategory(this);
    }

    public void changeStore(Store store) {
        if (Objects.nonNull(this.store)) {
            this.store.getReviews().remove(this);
        }
      
        this.store = store;
        store.getMenuCategories().add(this);
    }

}
