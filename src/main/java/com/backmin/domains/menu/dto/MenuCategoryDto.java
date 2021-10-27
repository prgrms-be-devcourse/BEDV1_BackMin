package com.backmin.domains.menu.dto;

import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.store.domain.Store;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuCategoryDto {

    private Long id;

    private String name;

    private List<Menu> menus = new ArrayList<>();

    private Store store;

}
