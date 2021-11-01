package com.backmin.domains.menu.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuAtStoreDetailResult {

    private Long id;

    private String name;

    private String imageUrl;

    private boolean isBest;

    private boolean isSoldOut;

    private boolean isPopular;

    private int price;

    private List<MenuOptionAtStoreDetailResult> menuOptions;

    public static MenuAtStoreDetailResult of(
            Long id,
            String name,
            String imageUrl,
            boolean isBest,
            boolean isSoldOut,
            boolean isPopular,
            int price,
            List<MenuOptionAtStoreDetailResult> menuOptions
    ) {
        MenuAtStoreDetailResult menuAtStoreDetailResult = new MenuAtStoreDetailResult();
        menuAtStoreDetailResult.setId(id);
        menuAtStoreDetailResult.setName(name);
        menuAtStoreDetailResult.setImageUrl(imageUrl);
        menuAtStoreDetailResult.setBest(isBest);
        menuAtStoreDetailResult.setSoldOut(isSoldOut);
        menuAtStoreDetailResult.setPopular(isPopular);
        menuAtStoreDetailResult.setPrice(price);
        menuAtStoreDetailResult.setMenuOptions(menuOptions);

        return menuAtStoreDetailResult;
    }

}
