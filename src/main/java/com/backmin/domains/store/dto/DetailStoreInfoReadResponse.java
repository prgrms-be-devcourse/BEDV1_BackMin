package com.backmin.domains.store.dto;

import com.backmin.domains.menu.dto.MenuInfoAtStoreDetail;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailStoreInfoReadResponse {

    private StoreInfoAtDetail store;

    private List<MenuInfoAtStoreDetail> bestMenus;

    private List<MenuInfoAtStoreDetail> menus;

    public static DetailStoreInfoReadResponse of(
            StoreInfoAtDetail store,
            List<MenuInfoAtStoreDetail> bestMenus,
            List<MenuInfoAtStoreDetail> menus
    ) {
        DetailStoreInfoReadResponse detailStoreInfoReadResponse = new DetailStoreInfoReadResponse();
        detailStoreInfoReadResponse.setStore(store);
        detailStoreInfoReadResponse.setBestMenus(bestMenus);
        detailStoreInfoReadResponse.setMenus(menus);

        return detailStoreInfoReadResponse;
    }

}
