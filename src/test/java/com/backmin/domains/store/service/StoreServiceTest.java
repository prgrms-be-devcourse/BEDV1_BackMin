package com.backmin.domains.store.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.backmin.config.exception.BusinessException;
import com.backmin.domains.BaseControllerTest;
import com.backmin.domains.common.dto.PageResult;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.domain.MenuOption;
import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.dto.response.DetailStoreReadResult;
import com.backmin.domains.store.dto.response.StoreAtListResult;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class StoreServiceTest extends BaseControllerTest {

    @Autowired
    private StoreService storeService;

    Category category;
    Store store1;
    Store store2;
    Menu menu1;
    Menu menu2;
    MenuOption option1;
    MenuOption option2;
    MenuOption option3;
    MenuOption option4;

    @BeforeEach
    void setUp() {

        category = categoryRepository.save(
                Category.builder()
                        .name("한식")
                        .build()
        );

        menu1 = menuRepository.save(Menu.of("엽기떡볶이", true, false, true, 17000, "겁나 맛있는 떡볶이입니다.", new ArrayList<>()));
        menu2 = menuRepository.save(Menu.of("치즈떡볶이", true, false, true, 18000, "겁나 맛있는 치즈 떡볶이입니다.", new ArrayList<>()));

        option1 = menuOptionRepository.save(MenuOption.of("치즈추가", 500));
        option2 = menuOptionRepository.save(MenuOption.of("사리추가", 600));
        option3 = menuOptionRepository.save(MenuOption.of("감자추가", 700));
        option4 = menuOptionRepository.save(MenuOption.of("고구마추가", 800));
        menu1.addMenuOption(option1);
        menu1.addMenuOption(option2);
        menu2.addMenuOption(option3);
        menu2.addMenuOption(option4);

        Member owner1 = Member.of("owner111@gmail.com",
                "12345",
                "010-1112-2222",
                "야이야이야",
                "인천광역시"
        );
        Member savedOwner = memberRepository.save(owner1);

        store1 = storeRepository.save(
                Store.of(
                        "동대문 엽기 떡볶이",
                        "070364532746",
                        "엽떡집입니다.",
                        1000,
                        30,
                        60,
                        2000,
                        true,
                        true,
                        this.category,
                        savedOwner,
                        new ArrayList<>()
                )
        );
        store2 = storeRepository.save(
                Store.of(
                        "참이맛 감자탕",
                        "070364532746",
                        "뼈해장국 맛집입니다.",
                        1000,
                        30,
                        60,
                        2000,
                        true,
                        true,
                        this.category,
                        savedOwner,
                        new ArrayList<>()
                )
        );
        store1.addMenu(menu1);
        store2.addMenu(menu2);
        store1 = storeRepository.save(store1);
        store2 = storeRepository.save(store2);
    }

    @Test
    @DisplayName("카테고리별 가게목록 조회 테스트")
    void test_readPagingStoresByCategoryId() {
        // given
        final int PAGE_SIZE = 2;
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        // when
        PageResult<StoreAtListResult> response = storeService.readPagingStoresByCategoryId(this.category.getId(), pageRequest);

        // then
        assertThat(response.getTotalCount(), is(Long.valueOf(2)));
        assertThat(response.getPageSize(), is(PAGE_SIZE));
        assertThat(response.isHasNext(), is(false));
    }

    @Test
    @DisplayName("가게 상세조회 테스트")
    void test_readDetailStoreInfo() {
        // given
        // when
        DetailStoreReadResult detailStoreReadResult = storeService.readDetailStore(store1.getId());

        // then
        assertThat(detailStoreReadResult.getStore(), notNullValue());
        assertThat(detailStoreReadResult.getBestMenus(), notNullValue());
        assertThat(detailStoreReadResult.getMenus(), notNullValue());

        List<Long> bestMenuIdsBefore = store1.getMenus().stream()
                .filter(Menu::isBest)
                .map(menu -> menu.getId())
                .collect(Collectors.toList());
        assertThat(detailStoreReadResult.getBestMenus().size(), is(bestMenuIdsBefore.size()));
        assertThat(detailStoreReadResult.getMenus().size(), is(store1.getMenus().size()));
    }

    @Test
    @DisplayName("존재하지 않는 가게에 대한 상세조회 테스트")
    void test_readDetailStoreInfo_invalid() {
        // given
        // when
        // then
//        assertThatThrownBy(() -> storeService.readDetailStore(200L))
//        .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("가게 이름으로 검색 테스트")
    void test_searchStoresByName() {
        PageRequest pageRequest = PageRequest.of(0, 2);

        PageResult<StoreAtListResult> searchedStorePage = storeService.searchStoresByName("감자탕", pageRequest);

        assertThat(searchedStorePage.getTotalCount(), is(1L));
        assertThat(searchedStorePage.getList().get(0).getStoreName(), containsString("감자탕"));
    }

}
