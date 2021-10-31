package com.backmin.domains.menu.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuInfoAtStoreList {

    private Long id;

    private String name;

    private String imageUrl;

    private boolean isBest;

    private boolean isSoldOut;

    private boolean isPopular;

    private int price;

    private List<MenuOptionInfoAtStoreList> menuOptions;

    public static MenuInfoAtStoreList of(
            Long id,
            String name,
            String imageUrl,
            boolean isBest,
            boolean isSoldOut,
            boolean isPopular,
            int price,
            List<MenuOptionInfoAtStoreList> menuOptions
    ) {
        MenuInfoAtStoreList menuInfoAtStoreList = new MenuInfoAtStoreList();
        menuInfoAtStoreList.setId(id);
        menuInfoAtStoreList.setName(name);
        menuInfoAtStoreList.setImageUrl(imageUrl);
        menuInfoAtStoreList.setBest(isBest);
        menuInfoAtStoreList.setSoldOut(isSoldOut);
        menuInfoAtStoreList.setPopular(isPopular);
        menuInfoAtStoreList.setPrice(price);
        menuInfoAtStoreList.setMenuOptions(menuOptions);

        return menuInfoAtStoreList;
    }

}
