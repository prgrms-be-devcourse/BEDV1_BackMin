package com.backmin.domains.store.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.domain.MenuOption;
import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.domain.CategoryRepository;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.domain.StoreRepository;
import com.backmin.domains.store.service.StoreService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("StoreController 테스트")
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("카테고리 별 가게 목록 조회 테스트")
    void test_list() throws Exception {
        mockMvc.perform(get("/api/v1/bm/categories/{categoryId}/stores", 1L)
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data.totalCount").exists())
                .andExpect(jsonPath("data.pageNumber").exists())
                .andExpect(jsonPath("data.pageSize").exists())
                .andExpect(jsonPath("data.hasNext").exists())
                .andExpect(jsonPath("data.list").exists())
                .andExpect(jsonPath("serverDatetime").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("가게 상세조회 테스트")
    void test_detail() throws Exception {

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
        Store savedStore = storeRepository.save(store1);

        mockMvc.perform(get("/api/v1/bm/stores/{storeId}", savedStore.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data.store").exists())
                .andExpect(jsonPath("data.bestMenus").exists())
                .andExpect(jsonPath("data.menus").exists())
                .andExpect(jsonPath("serverDatetime").exists())
                .andDo(print());
    }

}
