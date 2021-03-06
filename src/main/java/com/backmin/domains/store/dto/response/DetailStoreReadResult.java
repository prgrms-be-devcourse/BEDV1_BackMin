package com.backmin.domains.store.dto.response;

import com.backmin.domains.menu.dto.response.MenuAtStoreDetailResult;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailStoreReadResult {

    private StoreAtDetailResult store;

    private List<MenuAtStoreDetailResult> bestMenus;

    private List<MenuAtStoreDetailResult> menus;

}
