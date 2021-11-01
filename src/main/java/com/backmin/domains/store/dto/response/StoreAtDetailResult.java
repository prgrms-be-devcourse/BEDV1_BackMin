package com.backmin.domains.store.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreAtDetailResult {

    private Long storeId;

    private String storeName;

    private String phoneNumber;

    private String storeIntro;

    private int minOrderPrice;

    private int minDeliveryTime;

    private int maxDeliveryTime;

    private int deliveryTip;

    private boolean isPackage;

    public static StoreAtDetailResult of(
            Long storeId,
            String storeName,
            String phoneNumber,
            String storeIntro,
            int minOrderPrice,
            int minDeliveryTime,
            int maxDeliveryTime,
            int deliveryTip,
            boolean isPackage
    ) {
        StoreAtDetailResult storeAtDetailResult = new StoreAtDetailResult();
        storeAtDetailResult.setStoreId(storeId);
        storeAtDetailResult.setStoreName(storeName);
        storeAtDetailResult.setPhoneNumber(phoneNumber);
        storeAtDetailResult.setStoreIntro(storeIntro);
        storeAtDetailResult.setMinOrderPrice(minOrderPrice);
        storeAtDetailResult.setMinDeliveryTime(minDeliveryTime);
        storeAtDetailResult.setMaxDeliveryTime(maxDeliveryTime);
        storeAtDetailResult.setDeliveryTip(deliveryTip);
        storeAtDetailResult.setPackage(isPackage);

        return storeAtDetailResult;
    }

}
