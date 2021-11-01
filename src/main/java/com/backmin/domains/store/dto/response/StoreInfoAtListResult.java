package com.backmin.domains.store.dto.response;

import com.backmin.domains.menu.dto.response.MenuAtStoreListResult;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreInfoAtListResult {

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

    List<MenuAtStoreListResult> bestMenus = new ArrayList<>();

    public static StoreInfoAtListResult of(
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
            List<MenuAtStoreListResult> bestMenus
    ) {
        StoreInfoAtListResult storeInfoAtListResult = new StoreInfoAtListResult();
        storeInfoAtListResult.setStoreId(storeId);
        storeInfoAtListResult.setStoreName(storeName);
        storeInfoAtListResult.setStoreImageUrl(null);
        storeInfoAtListResult.setMinOrderPrice(minOrderPrice);
        storeInfoAtListResult.setMinDeliveryTime(minDeliveryTime);
        storeInfoAtListResult.setMaxDeliveryTime(maxDeliveryTime);
        storeInfoAtListResult.setStoreIntro(storeIntro);
        storeInfoAtListResult.setPackage(isPackage);
        storeInfoAtListResult.setDeliveryTip(deliveryTip);
        storeInfoAtListResult.setAverageScore(averageScore);
        storeInfoAtListResult.setTotalReviewCount(totalReviewCount);
        storeInfoAtListResult.setBestMenus(bestMenus);

        return storeInfoAtListResult;
    }

}
