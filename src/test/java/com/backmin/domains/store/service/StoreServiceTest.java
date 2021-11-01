package com.backmin.domains.store.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import com.backmin.domains.common.dto.PageDto;
import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.domain.MenuOption;
import com.backmin.domains.menu.domain.MenuOptionRepository;
import com.backmin.domains.menu.domain.MenuRepository;
import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.domain.CategoryRepository;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.domain.StoreRepository;
import com.backmin.domains.store.dto.StoreInfoAtList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class StoreServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MenuOptionRepository menuOptionRepository;

    @Autowired
    private StoreService storeService;

    @Test
    @DisplayName("카테고리별 가게목록 조회 테스트")
    void readPagingStoresByCategoryId() {
        // given
        final int PAGE_SIZE = 2;

        Category category1 = Category.builder()
                .name("한식")
                .build();
        categoryRepository.save(category1);

        MenuOption option1 = MenuOption.of("치즈추가", 500);
        MenuOption option2 = MenuOption.of("사리추가", 600);
        MenuOption option3 = MenuOption.of("감자추가", 700);
        MenuOption option4 = MenuOption.of("고구마추가", 800);
        List<MenuOption> menuOptions1 = List.of(option1, option2);
        List<MenuOption> menuOptions2 = List.of(option3, option4);
        menuOptionRepository.saveAll(menuOptions1);
        menuOptionRepository.saveAll(menuOptions2);

        Menu menu1 = Menu.of("엽기떡볶이", true, false, true, 17000, "겁나 맛있는 떡볶이입니다.", menuOptions1);
        Menu menu2 = Menu.of("치즈떡볶이", true, false, true, 18000, "겁나 맛있는 치즈 떡볶이입니다.", menuOptions2);
        List<Menu> menus = List.of(menu1, menu2);

        Store store1 = Store.of(
                "동대문 엽기 떡볶이",
                "070364532746",
                "엽떡집입니다.",
                1000,
                30,
                60,
                2000,
                true,
                true,
                category1,
                menus
        );

        Store store2 = Store.of(
                "참이맛 감자탕",
                "070364532746",
                "뼈해장국 맛집입니다.",
                1000,
                30,
                60,
                2000,
                true,
                true,
                category1,
                menus
        );

        List<Store> stores = List.of(store1, store2);
        storeRepository.saveAll(stores);

        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        PageDto<StoreInfoAtList> response = storeService.readPagingStoresByCategoryId(category1.getId(), pageRequest);

        // then
        assertThat(response.getTotalCount(), is(Long.valueOf(stores.size())));
        assertThat(response.getPageSize(), is(PAGE_SIZE));
        assertThat(response.isHasNext(), is(false));
    }

}
