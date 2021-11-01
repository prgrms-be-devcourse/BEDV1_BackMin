package com.backmin.domains.store.converter;

import com.backmin.domains.menu.dto.response.MenuAtStoreListResult;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.dto.response.StoreAtDetailResult;
import com.backmin.domains.store.dto.response.StoreInfoAtListResult;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StoreConverter {

    public StoreInfoAtListResult convertToStoreInfoAtList(Store store, List<MenuAtStoreListResult> menuAtStoreListResults, double averageReviewScore, int totalReviewCount) {
        return StoreInfoAtListResult.of(
                store.getId(),
                store.getName(),
                store.getStoreIntro(),
                store.getMinOrderPrice(),
                store.getMinDeliveryTime(),
                store.getMaxDeliveryTime(),
                store.getDeliveryTip(),
                store.isPackage(),
                averageReviewScore, // TODO : 평균 평점 넣기
                totalReviewCount, // TODO : 총 리뷰 수 넣기
                menuAtStoreListResults
        );
    }

    public StoreAtDetailResult convertToStoreInfoAtDetail(Store store) {
        return StoreAtDetailResult.of(
                store.getId(),
                store.getName(),
                store.getPhoneNumber(),
                store.getStoreIntro(),
                store.getMinOrderPrice(),
                store.getMinDeliveryTime(),
                store.getMaxDeliveryTime(),
                store.getDeliveryTip(),
                store.isPackage()
        );
    }

}
