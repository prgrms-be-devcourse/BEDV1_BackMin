package com.backmin.domains.menu.converter;

import com.backmin.domains.menu.domain.MenuOption;
import com.backmin.domains.menu.dto.MenuOptionInfoAtStoreDetail;
import com.backmin.domains.menu.dto.MenuOptionInfoAtStoreList;
import org.springframework.stereotype.Component;

@Component
public class MenuOptionConverter {

    public MenuOptionInfoAtStoreList convertMenuOptionToMenuOptionInfoAtStoreList(MenuOption menuOption) {
        return MenuOptionInfoAtStoreList.of(
                menuOption.getId(),
                menuOption.getName(),
                menuOption.getPrice()
        );
    }

    public MenuOptionInfoAtStoreDetail convertMenuOptionToMenuOptionInfoAtStoreDetail(MenuOption menuOption) {
        return MenuOptionInfoAtStoreDetail.of(
                menuOption.getId(),
                menuOption.getName(),
                menuOption.getPrice()
        );
    }

}
