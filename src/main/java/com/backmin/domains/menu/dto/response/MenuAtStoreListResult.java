package com.backmin.domains.menu.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuAtStoreListResult {

    private Long id;

    private String name;

    private String imageUrl;

    private boolean isBest;

    private boolean isSoldOut;

    private boolean isPopular;

    private int price;

    private List<MenuOptionAtStoreListResult> menuOptions;

    public static MenuAtStoreListResult of(
            Long id,
            String name,
            String imageUrl,
            boolean isBest,
            boolean isSoldOut,
            boolean isPopular,
            int price,
            List<MenuOptionAtStoreListResult> menuOptions
    ) {
        MenuAtStoreListResult menuAtStoreListResult = new MenuAtStoreListResult();
        menuAtStoreListResult.setId(id);
        menuAtStoreListResult.setName(name);
        menuAtStoreListResult.setImageUrl(imageUrl);
        menuAtStoreListResult.setBest(isBest);
        menuAtStoreListResult.setSoldOut(isSoldOut);
        menuAtStoreListResult.setPopular(isPopular);
        menuAtStoreListResult.setPrice(price);
        menuAtStoreListResult.setMenuOptions(menuOptions);

        return menuAtStoreListResult;
    }

}
