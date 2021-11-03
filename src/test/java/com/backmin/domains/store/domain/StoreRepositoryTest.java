package com.backmin.domains.store.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.domain.MenuOption;
import java.util.ArrayList;
import java.util.Collections;
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
@DisplayName("StoreRepository 테스트")
class StoreRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("카테고리 별 가게 목록 조회 테스트")
    void test_findStoreByCategoryId() {
        // given
        final int PAGE_SIZE = 2;

        Category category1 = Category.of("한식");
        categoryRepository.save(category1);

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
                new ArrayList<>() // menus
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
                new ArrayList<>() // menus
        );

        Store store3 = Store.of(
                "놀부 부대찌개",
                "070364532746",
                "부대찌개 맛집입니다.",
                1000,
                30,
                60,
                2000,
                true,
                true,
                category1,
                new ArrayList<>() // menus
        );

        Store store4 = Store.of(
                "마왕족발",
                "070364532746",
                "족발 맛집입니다.",
                1000,
                30,
                60,
                2000,
                true,
                true,
                category1,
                new ArrayList<>() // menus
        );

        List<Store> stores = List.of(store1, store2, store3, store4);
        storeRepository.saveAll(stores);

        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        Page<Store> storeByCategoryId =
                storeRepository.findPagingStoresByCategoryId(category1.getId(), pageRequest);

        // then
        assertThat(storeByCategoryId.getTotalElements(), is(Long.valueOf(stores.size())));
        assertThat(storeByCategoryId.getContent().size(), is(PAGE_SIZE));
        assertThat(storeByCategoryId.hasNext(), is(true));
    }

    @Test
    @DisplayName("가게 상세조회 테스트")
    void test_findStoreById() {
        // given
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
        storeRepository.save(store1);

        Store foundStore = storeRepository.findStoreById(store1.getId()).get();

        assertThat(foundStore, notNullValue());
        assertThat(foundStore.getId(), is(store1.getId()));

        List<Long> menuIdsBefore = menus.stream()
                .map(menu -> menu.getId())
                .collect(Collectors.toList());
        List<Long> menuIdsAfter = foundStore.getMenus().stream()
                .map(menu -> menu.getId())
                .collect(Collectors.toList());
        assertThat(menuIdsBefore, samePropertyValuesAs(menuIdsAfter));

    }

    @Test
    @DisplayName("가게 이름으로 검색 테스트")
    void test_searchStoresByName() {

        Category category1 = Category.builder()
                .name("한식")
                .build();
        categoryRepository.save(category1);

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
                Collections.emptyList()
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
                Collections.emptyList()
        );
        List<Store> stores = List.of(store1, store2);
        storeRepository.saveAll(stores);

        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<Store> storePage = storeRepository.findStoresByNameContaining("감자탕", pageRequest);

        assertThat(storePage.getTotalElements(), is(1L));
        assertThat(storePage.getContent().get(0).getName(), containsString("감자탕"));
    }

}
