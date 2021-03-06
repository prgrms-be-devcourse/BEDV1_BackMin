package com.backmin.domains.store.service;

import com.backmin.config.exception.BusinessException;
import com.backmin.domains.common.dto.PageResult;
import com.backmin.domains.common.enums.ErrorInfo;
import com.backmin.domains.menu.converter.MenuConverter;
import com.backmin.domains.menu.converter.MenuOptionConverter;
import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.domain.MenuRepository;
import com.backmin.domains.menu.dto.response.MenuAtStoreDetailResult;
import com.backmin.domains.menu.dto.response.MenuAtStoreListResult;
import com.backmin.domains.review.domain.ReviewRepository;
import com.backmin.domains.store.converter.StoreConverter;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.domain.StoreRepository;
import com.backmin.domains.store.dto.response.DetailStoreReadResult;
import com.backmin.domains.store.dto.response.StoreAtDetailResult;
import com.backmin.domains.store.dto.response.StoreAtListResult;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;
    private final StoreConverter storeConverter;
    private final MenuConverter menuConverter;
    private final MenuOptionConverter menuOptionConverter;

    public PageResult<StoreAtListResult> readPagingStoresByCategoryId(Long categoryId, Pageable pageRequest) {
        Page<Store> storePage = storeRepository.findPagingStoresByCategoryId(categoryId, pageRequest);

        return createPageDtoWithStoreInfoAtList(storeRepository.findPagingStoresByCategoryId(categoryId, pageRequest));
    }

    private PageResult<StoreAtListResult> createPageDtoWithStoreInfoAtList(Page<Store> searchedStorePage) {
        PageResult<StoreAtListResult> pageResult = new PageResult<>();
        pageResult.setList(get(searchedStorePage));
        pageResult.setHasNext(searchedStorePage.hasNext());
        pageResult.setPageSize(searchedStorePage.getSize());
        pageResult.setPageNumber(searchedStorePage.getNumber());
        pageResult.setTotalCount(searchedStorePage.getTotalElements());
        return pageResult;
    }

    public DetailStoreReadResult readDetailStore(Long storeId) {
        Store foundStore = storeRepository.findStoreById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorInfo.STORE_NOT_FOUND));

        StoreAtDetailResult storeInfo = storeConverter.convertToStoreInfoAtDetail(foundStore);

        List<MenuAtStoreDetailResult> bestMenuInfos = menuRepository.findBestMenusByStore(storeId).stream()
                .map(this::getConvertedMenuInfoAtStoreDetail)
                .collect(Collectors.toList());

        List<MenuAtStoreDetailResult> menuInfos = foundStore.getMenus().stream()
                .map(this::getConvertedMenuInfoAtStoreDetail)
                .collect(Collectors.toList());

        DetailStoreReadResult detailStoreReadResult = new DetailStoreReadResult();
        detailStoreReadResult.setStore(storeInfo);
        detailStoreReadResult.setBestMenus(bestMenuInfos);
        detailStoreReadResult.setMenus(menuInfos);

        return detailStoreReadResult;
    }

    public PageResult<StoreAtListResult> searchStoresByName(String storeName, Pageable pageRequest) {
        Page<Store> searchedStorePage = storeRepository.findStoresByNameContaining(storeName, pageRequest);

        return createPageDtoWithStoreInfoAtList(storeRepository.findStoresByNameContaining(storeName, pageRequest));
    }

    private List<StoreAtListResult> get(Page<Store> searchedStorePage) {
        return searchedStorePage.getContent().stream()
                .map(store -> storeConverter.convertToStoreInfoAtList(
                        store,
                        menuRepository.findBestMenusByStore(store.getId()).stream()
                                .map(menu -> getConvertedMenuInfoAtStoreList(menu))
                                .collect(Collectors.toList()),
                        reviewRepository.getReviewAverageByStore(store.getId()),
                        reviewRepository.getReviewTotalCountByStore(store.getId())
                ))
                .collect(Collectors.toList());
    }

    private MenuAtStoreDetailResult getConvertedMenuInfoAtStoreDetail(Menu menu) {
        return menuConverter.convertMenuToMenuInfoAtStoreDetail(
                menu,
                menu.getMenuOptions().stream()
                        .map(menuOptionConverter::convertMenuOptionToMenuOptionInfoAtStoreDetail)
                        .collect(Collectors.toList()));
    }

    private MenuAtStoreListResult getConvertedMenuInfoAtStoreList(Menu menu) {
        return menuConverter.convertMenuToMenuInfoAtStoreList(
                menu,
                menu.getMenuOptions().stream()
                        .map(menuOptionConverter::convertMenuOptionToMenuOptionInfoAtStoreList)
                        .collect(Collectors.toList()));
    }

}
