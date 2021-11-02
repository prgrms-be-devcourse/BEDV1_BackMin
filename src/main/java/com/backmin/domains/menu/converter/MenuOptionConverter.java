package com.backmin.domains.menu.converter;

import com.backmin.domains.menu.domain.MenuOption;
import com.backmin.domains.menu.dto.response.MenuOptionAtStoreDetailResult;
import com.backmin.domains.menu.dto.response.MenuOptionAtStoreListResult;
import org.springframework.stereotype.Component;

@Component
public class MenuOptionConverter {

    public MenuOptionAtStoreListResult convertMenuOptionToMenuOptionInfoAtStoreList(MenuOption menuOption) {
        MenuOptionAtStoreListResult menuOptionAtStoreListResult = new MenuOptionAtStoreListResult();
        menuOptionAtStoreListResult.setOptionId(menuOption.getId());
        menuOptionAtStoreListResult.setOptionName(menuOption.getName());
        menuOptionAtStoreListResult.setOptionPrice(menuOption.getPrice());

        return menuOptionAtStoreListResult;
    }

    public MenuOptionAtStoreDetailResult convertMenuOptionToMenuOptionInfoAtStoreDetail(MenuOption menuOption) {
        MenuOptionAtStoreDetailResult menuOptionAtStoreDetailResult = new MenuOptionAtStoreDetailResult();
        menuOptionAtStoreDetailResult.setOptionId(menuOption.getId());
        menuOptionAtStoreDetailResult.setOptionName(menuOption.getName());
        menuOptionAtStoreDetailResult.setOptionPrice(menuOption.getPrice());

        return menuOptionAtStoreDetailResult;
    }

}
