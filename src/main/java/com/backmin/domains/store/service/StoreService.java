package com.backmin.domains.store.service;

import com.backmin.domains.common.dto.PageDto;
import com.backmin.domains.common.exception.StoreNotFoundException;
import com.backmin.domains.menu.converter.MenuConverter;
import com.backmin.domains.menu.converter.MenuOptionConverter;
import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.domain.MenuRepository;
import com.backmin.domains.menu.dto.MenuInfoAtStoreDetail;
import com.backmin.domains.menu.dto.MenuInfoAtStoreList;
import com.backmin.domains.review.domain.ReviewRepository;
import com.backmin.domains.store.converter.StoreConverter;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.domain.StoreRepository;
import com.backmin.domains.store.dto.DetailStoreInfoReadResponse;
import com.backmin.domains.store.dto.StoreInfoAtDetail;
import com.backmin.domains.store.dto.StoreInfoAtList;
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

    public PageDto<StoreInfoAtList> readPagingStoresByCategoryId(Long categoryId, Pageable pageRequest) {
        Page<Store> storePage = storeRepository.findPagingStoresByCategoryId(categoryId, pageRequest);

        return getPageDtoWithStoreInfoAtList(storePage);
    }

    public DetailStoreInfoReadResponse readDetailStore(Long storeId) {
        Store foundStore = storeRepository.findStoreById(storeId)
                .orElseThrow(() -> new StoreNotFoundException());

        StoreInfoAtDetail storeInfo = storeConverter.convertToStoreInfoAtDetail(foundStore);

        List<MenuInfoAtStoreDetail> bestMenuInfos = menuRepository.findBestMenusByStore(storeId).stream()
                .map(this::getConvertedMenuInfoAtStoreDetail)
                .collect(Collectors.toList());

        List<MenuInfoAtStoreDetail> menuInfos = foundStore.getMenus().stream()
                .map(this::getConvertedMenuInfoAtStoreDetail
                ).collect(Collectors.toList());

        return DetailStoreInfoReadResponse.of(
                storeInfo,
                bestMenuInfos,
                menuInfos
        );
    }

    public PageDto<StoreInfoAtList> searchStoresByName(String storeName, Pageable pageRequest) {
        Page<Store> searchedStorePage = storeRepository.findStoresByNameContaining(storeName, pageRequest);

        return getPageDtoWithStoreInfoAtList(searchedStorePage);
    }

    private PageDto<StoreInfoAtList> getPageDtoWithStoreInfoAtList(Page<Store> searchedStorePage) {
        List<StoreInfoAtList> searchedStoreInfoAtLists = searchedStorePage.getContent().stream()
                .map(store -> storeConverter.convertToStoreInfoAtList(
                        store,
                        menuRepository.findBestMenusByStore(store.getId()).stream()
                                .map(menu -> getConvertedMenuInfoAtStoreList(menu))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return PageDto.<StoreInfoAtList>builder()
                .list(searchedStoreInfoAtLists)
                .hasNext(searchedStorePage.hasNext())
                .pageSize(searchedStorePage.getSize())
                .pageNumber(searchedStorePage.getNumber())
                .totalCount(searchedStorePage.getTotalElements())
                .build();
    }

    private MenuInfoAtStoreDetail getConvertedMenuInfoAtStoreDetail(Menu menu) {
        return menuConverter.convertMenuToMenuInfoAtStoreDetail(
                menu,
                menu.getMenuOptions().stream()
                        .map(menuOptionConverter::convertMenuOptionToMenuOptionInfoAtStoreDetail)
                        .collect(Collectors.toList()));
    }

    private MenuInfoAtStoreList getConvertedMenuInfoAtStoreList(Menu menu) {
        return menuConverter.convertMenuToMenuInfoAtStoreList(
                menu,
                menu.getMenuOptions().stream()
                        .map(menuOptionConverter::convertMenuOptionToMenuOptionInfoAtStoreList)
                        .collect(Collectors.toList()));
    }

}
