package com.backmin.domains.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreInfoAtDetail {

    private Long storeId;

    private String storeName;

    private String phoneNumber;

    private String storeIntro;

    private int minOrderPrice;

    private int minDeliveryTime;

    private int maxDeliveryTime;

    private int deliveryTip;

    private boolean isPackage;

    public static StoreInfoAtDetail of(
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
        StoreInfoAtDetail storeInfoAtDetail = new StoreInfoAtDetail();
        storeInfoAtDetail.setStoreId(storeId);
        storeInfoAtDetail.setStoreName(storeName);
        storeInfoAtDetail.setPhoneNumber(phoneNumber);
        storeInfoAtDetail.setStoreIntro(storeIntro);
        storeInfoAtDetail.setMinOrderPrice(minOrderPrice);
        storeInfoAtDetail.setMinDeliveryTime(minDeliveryTime);
        storeInfoAtDetail.setMaxDeliveryTime(maxDeliveryTime);
        storeInfoAtDetail.setDeliveryTip(deliveryTip);
        storeInfoAtDetail.setPackage(isPackage);

        return storeInfoAtDetail;
    }

}
