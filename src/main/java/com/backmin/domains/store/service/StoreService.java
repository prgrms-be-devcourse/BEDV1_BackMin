package com.backmin.domains.store.service;

import com.backmin.domains.common.dto.PageDto;
import com.backmin.domains.common.exception.StoreNotFoundException;
import com.backmin.domains.menu.converter.MenuConverter;
import com.backmin.domains.menu.converter.MenuOptionConverter;
import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.domain.MenuRepository;
import com.backmin.domains.menu.dto.MenuInfoAtStoreDetail;
import com.backmin.domains.menu.dto.MenuInfoAtStoreList;
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
    private final StoreConverter storeConverter;
    private final MenuConverter menuConverter;
    private final MenuOptionConverter menuOptionConverter;

    public PageDto<StoreInfoAtList> readPagingStoresByCategoryId(Long categoryId, Pageable pageRequest) {
        Page<Store> storeSlice = storeRepository.findPagingStoresByCategoryId(categoryId, pageRequest);

        List<StoreInfoAtList> storeInfoAtLists = storeSlice.getContent().stream()
                .map(store -> storeConverter.convertToStoreInfoAtList(
                        store,
                        menuRepository.findBestMenusByStore(store.getId()).stream()
                                .map(menu -> getConvertedMenuInfoAtStoreList(menu))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        // TODO : 리뷰 평균 점수 저장

        return PageDto.<StoreInfoAtList>builder()
                .list(storeInfoAtLists)
                .hasNext(storeSlice.hasNext())
                .pageSize(storeSlice.getSize())
                .pageNumber(storeSlice.getNumber())
                .totalCount(storeSlice.getTotalElements())
                .build();
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

    private MenuInfoAtStoreDetail getConvertedMenuInfoAtStoreDetail(Menu menu) {
        return menuConverter.convertMenuToMenuInfoAtStoreDetail(
                menu,
                menu.getMenuOptions().stream()
                        .map(menuOptionConverter::convertMenuOptionToMenuOptionInfoAtStoreDetail).collect(Collectors.toList()));
    }

    private MenuInfoAtStoreList getConvertedMenuInfoAtStoreList(Menu menu) {
        return menuConverter.convertMenuToMenuInfoAtStoreList(
                menu,
                menu.getMenuOptions().stream()
                        .map(menuOptionConverter::convertMenuOptionToMenuOptionInfoAtStoreList)
                        .collect(Collectors.toList()));
    }

}
