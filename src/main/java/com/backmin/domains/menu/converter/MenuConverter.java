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
        return MenuAtStoreListResult.of(
                menu.getId(),
                menu.getName(),
                null,
                menu.isBest(),
                menu.isSoldOut(),
                menu.isPopular(),
                menu.getPrice(),
                menuOptionAtStoreListResults
        );
    }

    public MenuAtStoreDetailResult convertMenuToMenuInfoAtStoreDetail(Menu menu, List<MenuOptionAtStoreDetailResult> menuOptionAtStoreDetailResults) {
        return MenuAtStoreDetailResult.of(
                menu.getId(),
                menu.getName(),
                null,
                menu.isBest(),
                menu.isSoldOut(),
                menu.isPopular(),
                menu.getPrice(),
                menuOptionAtStoreDetailResults
        );
    }

}
