package com.backmin.domains.menu.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.domain.CategoryRepository;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.domain.StoreRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("MenuRepository 테스트")
class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        menuRepository.deleteAll();
        storeRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("가게 별 대표메뉴 조회 테스트")
    void test_findBestMenuByStore() {
        // given
        MenuOption option1 = MenuOption.of("치즈추가", 500);
        MenuOption option2 = MenuOption.of("사리추가", 600);
        List<MenuOption> menuOptions1 = List.of(option1, option2);

        Menu menu1 = Menu.of("엽기떡볶이", true, false, true, 17000, "겁나 맛있는 떡볶이입니다.", menuOptions1);
        Menu menu2 = Menu.of("치즈떡볶이", false, false, true, 18000, "겁나 맛있는 치즈 떡볶이입니다.", menuOptions1);
        Menu menu3 = Menu.of("로제떡볶이", false, false, true, 18000, "겁나 맛있는 치즈 떡볶이입니다.", menuOptions1);
        List<Menu> menus = List.of(menu1, menu2, menu3);

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
                menus
        );
        storeRepository.save(store1);

        // when
        List<Menu> bestMenusByStore = menuRepository.findBestMenusByStore(store1.getId());

        // then
        List<Long> filteringIdsBefore = menus.stream()
                .filter(Menu::isBest)
                .map(menu -> menu.getId())
                .sorted(Long::compareTo)
                .collect(Collectors.toList());

        List<Long> idsAfter = bestMenusByStore.stream()
                .map(menu -> menu.getId())
                .sorted(Long::compareTo)
                .collect(Collectors.toList());

        assertThat(bestMenusByStore.size(), is(filteringIdsBefore.size()));
        assertThat(filteringIdsBefore, samePropertyValuesAs(idsAfter));
    }

}
