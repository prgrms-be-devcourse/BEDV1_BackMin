package com.backmin.domains.menu.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuInfoAtStoreDetail {

    private Long id;

    private String name;

    private String imageUrl;

    private boolean isBest;

    private boolean isSoldOut;

    private boolean isPopular;

    private int price;

    private List<MenuOptionInfoAtStoreDetail> menuOptions;

    public static MenuInfoAtStoreDetail of(
            Long id,
            String name,
            String imageUrl,
            boolean isBest,
            boolean isSoldOut,
            boolean isPopular,
            int price,
            List<MenuOptionInfoAtStoreDetail> menuOptions
    ) {
        MenuInfoAtStoreDetail menuInfoAtStoreDetail = new MenuInfoAtStoreDetail();
        menuInfoAtStoreDetail.setId(id);
        menuInfoAtStoreDetail.setName(name);
        menuInfoAtStoreDetail.setImageUrl(imageUrl);
        menuInfoAtStoreDetail.setBest(isBest);
        menuInfoAtStoreDetail.setSoldOut(isSoldOut);
        menuInfoAtStoreDetail.setPopular(isPopular);
        menuInfoAtStoreDetail.setPrice(price);
        menuInfoAtStoreDetail.setMenuOptions(menuOptions);

        return menuInfoAtStoreDetail;
    }

}
