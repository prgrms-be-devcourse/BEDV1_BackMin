package com.backmin.domains.store.converter;

import com.backmin.domains.menu.dto.response.MenuAtStoreListResult;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.dto.response.StoreAtDetailResult;
import com.backmin.domains.store.dto.response.StoreAtListResult;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StoreConverter {

    public StoreAtListResult convertToStoreInfoAtList(Store store, List<MenuAtStoreListResult> menuAtStoreListResults, double averageReviewScore,
            int totalReviewCount) {
        StoreAtListResult storeAtListResult = new StoreAtListResult();
        storeAtListResult.setStoreId(store.getId());
        storeAtListResult.setStoreName(store.getName());
        storeAtListResult.setStoreImageUrl(null);
        storeAtListResult.setStoreIntro(store.getStoreIntro());
        storeAtListResult.setMinOrderPrice(store.getMinOrderPrice());
        storeAtListResult.setMinDeliveryTime(store.getMinDeliveryTime());
        storeAtListResult.setMaxDeliveryTime(store.getMaxDeliveryTime());
        storeAtListResult.setPackage(store.isPackage());
        storeAtListResult.setDeliveryTip(store.getDeliveryTip());
        storeAtListResult.setAverageScore(averageReviewScore);
        storeAtListResult.setTotalReviewCount(totalReviewCount);
        storeAtListResult.setBestMenus(menuAtStoreListResults);

        return storeAtListResult;
    }

    public StoreAtDetailResult convertToStoreInfoAtDetail(Store store) {
        StoreAtDetailResult storeAtDetailResult = new StoreAtDetailResult();
        storeAtDetailResult.setStoreId(store.getId());
        storeAtDetailResult.setStoreName(store.getName());
        storeAtDetailResult.setPhoneNumber(store.getPhoneNumber());
        storeAtDetailResult.setStoreIntro(store.getStoreIntro());
        storeAtDetailResult.setMinOrderPrice(store.getMinOrderPrice());
        storeAtDetailResult.setMinDeliveryTime(store.getMinDeliveryTime());
        storeAtDetailResult.setMaxDeliveryTime(store.getMaxDeliveryTime());
        storeAtDetailResult.setDeliveryTip(store.getDeliveryTip());
        storeAtDetailResult.setPackage(store.isPackage());

        return storeAtDetailResult;
    }

}
