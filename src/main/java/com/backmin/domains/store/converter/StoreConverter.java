package com.backmin.domains.store.converter;

import com.backmin.domains.menu.dto.MenuInfoAtStoreList;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.dto.StoreInfoAtDetail;
import com.backmin.domains.store.dto.StoreInfoAtList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StoreConverter {

    public StoreInfoAtList convertToStoreInfoAtList(Store store, List<MenuInfoAtStoreList> menuInfoAtStoreLists) {
        return StoreInfoAtList.of(
                store.getId(),
                store.getName(),
                store.getStoreIntro(),
                store.getMinOrderPrice(),
                store.getMinDeliveryTime(),
                store.getMaxDeliveryTime(),
                store.getDeliveryTip(),
                store.isPackage(),
                0, // TODO : 평균 평점 넣기
                0, // TODO : 총 리뷰 수 넣기
                menuInfoAtStoreLists
        );
    }

    public StoreInfoAtDetail convertToStoreInfoAtDetail(Store store) {
        return StoreInfoAtDetail.of(
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
