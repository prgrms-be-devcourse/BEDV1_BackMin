package com.backmin.domains.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDto {

    private Long id;

    private String name;

    private String phoneNumber;

    private int minOrderPrice;

    private int minDeliveryTime;

    private int maxDeliveryTime;

    private String storeIntro;

    private boolean isService;

    private String mainIntro;

    private boolean isPackage;

    private int deliveryTip;

}
