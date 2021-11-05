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

}
