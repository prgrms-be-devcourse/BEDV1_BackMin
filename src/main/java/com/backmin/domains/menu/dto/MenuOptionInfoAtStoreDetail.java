package com.backmin.domains.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuOptionInfoAtStoreDetail {

    private Long optionId;

    private String optionName;

    private int optionPrice;

    public static MenuOptionInfoAtStoreDetail of(Long optionId, String optionName, int optionPrice) {
        MenuOptionInfoAtStoreDetail menuOptionInfoAtStoreDetail = new MenuOptionInfoAtStoreDetail();
        menuOptionInfoAtStoreDetail.setOptionId(optionId);
        menuOptionInfoAtStoreDetail.setOptionName(optionName);
        menuOptionInfoAtStoreDetail.setOptionPrice(optionPrice);

        return menuOptionInfoAtStoreDetail;
    }

}
