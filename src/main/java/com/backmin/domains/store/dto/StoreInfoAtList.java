package com.backmin.domains.store.dto;

import com.backmin.domains.menu.dto.MenuInfoAtStoreList;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreInfoAtList {

    private Long storeId;

    private String storeName;

    private String storeIntro;

    private String storeImageUrl;

    private int minOrderPrice;

    private int deliveryTip;

    private int minDeliveryTime;

    private int maxDeliveryTime;

    private boolean isPackage;

    private double averageScore;

    int totalReviewCount;

    List<MenuInfoAtStoreList> bestMenus = new ArrayList<>();

    public static StoreInfoAtList of(
            Long storeId,
            String storeName,
            String storeIntro,
            int minOrderPrice,
            int minDeliveryTime,
            int maxDeliveryTime,
            int deliveryTip,
            boolean isPackage,
            double averageScore,
            int totalReviewCount,
            List<MenuInfoAtStoreList> bestMenus
    ) {
        StoreInfoAtList storeInfoAtList = new StoreInfoAtList();
        storeInfoAtList.setStoreId(storeId);
        storeInfoAtList.setStoreName(storeName);
        storeInfoAtList.setStoreImageUrl(null);
        storeInfoAtList.setMinOrderPrice(minOrderPrice);
        storeInfoAtList.setMinDeliveryTime(minDeliveryTime);
        storeInfoAtList.setMaxDeliveryTime(maxDeliveryTime);
        storeInfoAtList.setStoreIntro(storeIntro);
        storeInfoAtList.setPackage(isPackage);
        storeInfoAtList.setDeliveryTip(deliveryTip);
        storeInfoAtList.setAverageScore(averageScore);
        storeInfoAtList.setTotalReviewCount(totalReviewCount);
        storeInfoAtList.setBestMenus(bestMenus);

        return storeInfoAtList;
    }

}
