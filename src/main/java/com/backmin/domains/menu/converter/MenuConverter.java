package com.backmin.domains.menu.converter;

import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.dto.MenuInfoAtStoreList;
import com.backmin.domains.menu.dto.MenuOptionInfoAtStoreList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MenuConverter {

    public MenuInfoAtStoreList convertMenuToMenuInfoAtStoreList(Menu menu, List<MenuOptionInfoAtStoreList> menuOptionInfoAtStoreLists) {
        return MenuInfoAtStoreList.of(
                menu.getId(),
                menu.getName(),
                null,
                menu.isBest(),
                menu.isSoldOut(),
                menu.isPopular(),
                menu.getPrice(),
                menuOptionInfoAtStoreLists
        );
    }

}
