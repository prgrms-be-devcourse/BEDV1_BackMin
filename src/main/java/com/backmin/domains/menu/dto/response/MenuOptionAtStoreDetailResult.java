package com.backmin.domains.menu.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuOptionAtStoreDetailResult {

    private Long optionId;

    private String optionName;

    private int optionPrice;

    public static MenuOptionAtStoreDetailResult of(Long optionId, String optionName, int optionPrice) {
        MenuOptionAtStoreDetailResult menuOptionAtStoreDetailResult = new MenuOptionAtStoreDetailResult();
        menuOptionAtStoreDetailResult.setOptionId(optionId);
        menuOptionAtStoreDetailResult.setOptionName(optionName);
        menuOptionAtStoreDetailResult.setOptionPrice(optionPrice);

        return menuOptionAtStoreDetailResult;
    }

}
