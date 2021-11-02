package com.backmin.domains.menu.converter;

import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.dto.response.MenuAtStoreDetailResult;
import com.backmin.domains.menu.dto.response.MenuAtStoreListResult;
import com.backmin.domains.menu.dto.response.MenuOptionAtStoreDetailResult;
import com.backmin.domains.menu.dto.response.MenuOptionAtStoreListResult;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MenuConverter {

    public MenuAtStoreListResult convertMenuToMenuInfoAtStoreList(Menu menu, List<MenuOptionAtStoreListResult> menuOptionAtStoreListResults) {
        MenuAtStoreListResult menuAtStoreListResult = new MenuAtStoreListResult();
        menuAtStoreListResult.setId(menu.getId());
        menuAtStoreListResult.setName(menu.getName());
        menuAtStoreListResult.setImageUrl(null);
        menuAtStoreListResult.setBest(menu.isBest());
        menuAtStoreListResult.setSoldOut(menu.isSoldOut());
        menuAtStoreListResult.setPopular(menu.isPopular());
        menuAtStoreListResult.setPrice(menu.getPrice());
        menuAtStoreListResult.setMenuOptions(menuOptionAtStoreListResults);

        return menuAtStoreListResult;
    }

    public MenuAtStoreDetailResult convertMenuToMenuInfoAtStoreDetail(Menu menu, List<MenuOptionAtStoreDetailResult> menuOptionAtStoreDetailResults) {
        MenuAtStoreDetailResult menuAtStoreDetailResult = new MenuAtStoreDetailResult();
        menuAtStoreDetailResult.setId(menu.getId());
        menuAtStoreDetailResult.setName(menu.getName());
        menuAtStoreDetailResult.setImageUrl(null);
        menuAtStoreDetailResult.setBest(menu.isBest());
        menuAtStoreDetailResult.setSoldOut(menu.isSoldOut());
        menuAtStoreDetailResult.setPopular(menu.isPopular());
        menuAtStoreDetailResult.setPrice(menu.getPrice());
        menuAtStoreDetailResult.setMenuOptions(menuOptionAtStoreDetailResults);

        return menuAtStoreDetailResult;
    }

}
