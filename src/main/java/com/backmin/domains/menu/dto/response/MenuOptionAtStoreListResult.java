package com.backmin.domains.menu.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuOptionAtStoreListResult {

    private Long optionId;

    private String optionName;

    private int optionPrice;

    public static MenuOptionAtStoreListResult of(Long optionId, String optionName, int optionPrice) {
        MenuOptionAtStoreListResult menuOptionAtStoreListResult = new MenuOptionAtStoreListResult();
        menuOptionAtStoreListResult.setOptionId(optionId);
        menuOptionAtStoreListResult.setOptionName(optionName);
        menuOptionAtStoreListResult.setOptionPrice(optionPrice);

        return menuOptionAtStoreListResult;
    }

}
