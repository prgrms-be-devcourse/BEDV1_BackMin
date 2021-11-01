package com.backmin.domains.menu.converter;

import com.backmin.domains.menu.domain.MenuOption;
import com.backmin.domains.menu.dto.response.MenuOptionAtStoreDetailResult;
import com.backmin.domains.menu.dto.response.MenuOptionAtStoreListResult;
import org.springframework.stereotype.Component;

@Component
public class MenuOptionConverter {

    public MenuOptionAtStoreListResult convertMenuOptionToMenuOptionInfoAtStoreList(MenuOption menuOption) {
        return MenuOptionAtStoreListResult.of(
                menuOption.getId(),
                menuOption.getName(),
                menuOption.getPrice()
        );
    }

    public MenuOptionAtStoreDetailResult convertMenuOptionToMenuOptionInfoAtStoreDetail(MenuOption menuOption) {
        return MenuOptionAtStoreDetailResult.of(
                menuOption.getId(),
                menuOption.getName(),
                menuOption.getPrice()
        );
    }

}
