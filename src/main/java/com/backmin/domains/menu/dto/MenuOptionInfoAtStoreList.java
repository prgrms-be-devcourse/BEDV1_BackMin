package com.backmin.domains.menu.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuOptionInfoAtStoreList {

    private Long optionId;

    private String optionName;

    private int optionPrice;

    public static MenuOptionInfoAtStoreList of(Long optionId, String optionName, int optionPrice) {
        MenuOptionInfoAtStoreList menuOptionInfoAtStoreList = new MenuOptionInfoAtStoreList();
        menuOptionInfoAtStoreList.setOptionId(optionId);
        menuOptionInfoAtStoreList.setOptionName(optionName);
        menuOptionInfoAtStoreList.setOptionPrice(optionPrice);

        return menuOptionInfoAtStoreList;
    }

}
