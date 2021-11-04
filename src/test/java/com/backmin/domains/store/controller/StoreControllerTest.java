package com.backmin.domains.store.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backmin.domains.BaseControllerTest;
import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.domain.MenuOption;
import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.domain.Store;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

// todo : 이미지가 추가되면 RestDocs에 ImageUrl 필드가 NULL인것 변경해야한다.
@DisplayName("StoreController 테스트")
class StoreControllerTest extends BaseControllerTest {

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
        menuRepository.save(menu1);
        menuRepository.save(menu2);

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
                        category,
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
                        category,
                        new ArrayList<>()
                )
        );
        store1.addMenu(menu1);
        store2.addMenu(menu2);
        store1 = storeRepository.save(store1);
        store2 = storeRepository.save(store2);
    }

    @Test
    @DisplayName("카테고리 별 가게 목록 조회 테스트")
    void test_list() throws Exception {
        mockMvc.perform(get("/api/v1/bm/categories/{categoryId}/stores", category.getId())
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
                .andDo(print())
                .andDo(document("store-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터").optional(),
                                fieldWithPath("data.totalCount").type(JsonFieldType.NUMBER).description("전체 데이터 개수"),
                                fieldWithPath("data.pageNumber").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                fieldWithPath("data.pageSize").type(JsonFieldType.NUMBER).description("한 페이지 크기"),
                                fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                                fieldWithPath("data.list").type(JsonFieldType.ARRAY).description("가게 목록"),
                                fieldWithPath("data.list[].storeId").type(JsonFieldType.NUMBER).description("가게 Id"),
                                fieldWithPath("data.list[].storeName").type(JsonFieldType.STRING).description("가게명"),
                                fieldWithPath("data.list[].storeIntro").type(JsonFieldType.STRING).description("가게소개"),
                                fieldWithPath("data.list[].storeImageUrl").type(JsonFieldType.NULL).description("가게 썸네일"),
                                fieldWithPath("data.list[].minOrderPrice").type(JsonFieldType.NUMBER).description("최소주문금액"),
                                fieldWithPath("data.list[].deliveryTip").type(JsonFieldType.NUMBER).description("배달팁"),
                                fieldWithPath("data.list[].minDeliveryTime").type(JsonFieldType.NUMBER).description("최소배달시간"),
                                fieldWithPath("data.list[].maxDeliveryTime").type(JsonFieldType.NUMBER).description("최대배달시간"),
                                fieldWithPath("data.list[].package").type(JsonFieldType.BOOLEAN).description("포장가능여부"),
                                fieldWithPath("data.list[].averageScore").type(JsonFieldType.NUMBER).description("평균평점"),
                                fieldWithPath("data.list[].totalReviewCount").type(JsonFieldType.NUMBER).description("전체 리뷰 수"),
                                fieldWithPath("data.list[].bestMenus").type(JsonFieldType.ARRAY).description("대표메뉴"),
                                fieldWithPath("data.list[].bestMenus[].id").type(JsonFieldType.NUMBER).description("메뉴Id"),
                                fieldWithPath("data.list[].bestMenus[].name").type(JsonFieldType.STRING).description("메뉴명"),
                                fieldWithPath("data.list[].bestMenus[].imageUrl").type(JsonFieldType.NULL).description("메뉴썸네일"),
                                fieldWithPath("data.list[].bestMenus[].best").type(JsonFieldType.BOOLEAN).description("대표메뉴여부"),
                                fieldWithPath("data.list[].bestMenus[].soldOut").type(JsonFieldType.BOOLEAN).description("품절여부"),
                                fieldWithPath("data.list[].bestMenus[].popular").type(JsonFieldType.BOOLEAN).description("인기메뉴여부"),
                                fieldWithPath("data.list[].bestMenus[].price").type(JsonFieldType.NUMBER).description("메뉴가격"),
                                fieldWithPath("data.list[].bestMenus[].menuOptions[]").type(JsonFieldType.ARRAY).description("메뉴옵션"),
                                fieldWithPath("data.list[].bestMenus[].menuOptions[].optionId").type(JsonFieldType.NUMBER).description("메뉴옵션 Id"),
                                fieldWithPath("data.list[].bestMenus[].menuOptions[].optionName").type(JsonFieldType.STRING).description("메뉴옵션명"),
                                fieldWithPath("data.list[].bestMenus[].menuOptions[].optionPrice").type(JsonFieldType.NUMBER).description("메뉴옵션가격")
                        )));
    }

    @Test
    @DisplayName("가게 상세조회 테스트")
    void test_detail() throws Exception {
        mockMvc.perform(get("/api/v1/bm/stores/{storeId}", store1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data.store").exists())
                .andExpect(jsonPath("data.bestMenus").exists())
                .andExpect(jsonPath("data.menus").exists())
                .andExpect(jsonPath("serverDatetime").exists())
                .andDo(print())
                .andDo(document("store-detail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터").optional(),
                                fieldWithPath("data.store").type(JsonFieldType.OBJECT).description("가게"),
                                fieldWithPath("data.store.storeId").type(JsonFieldType.NUMBER).description("가게 Id"),
                                fieldWithPath("data.store.storeName").type(JsonFieldType.STRING).description("가게명"),
                                fieldWithPath("data.store.phoneNumber").type(JsonFieldType.STRING).description("가게전화번호"),
                                fieldWithPath("data.store.storeIntro").type(JsonFieldType.STRING).description("가게소개"),
                                fieldWithPath("data.store.minOrderPrice").type(JsonFieldType.NUMBER).description("최소주문금액"),
                                fieldWithPath("data.store.deliveryTip").type(JsonFieldType.NUMBER).description("배달팁"),
                                fieldWithPath("data.store.minDeliveryTime").type(JsonFieldType.NUMBER).description("최소배달시간"),
                                fieldWithPath("data.store.maxDeliveryTime").type(JsonFieldType.NUMBER).description("최대배달시간"),
                                fieldWithPath("data.store.package").type(JsonFieldType.BOOLEAN).description("포장가능여부"),
                                fieldWithPath("data.bestMenus").type(JsonFieldType.ARRAY).description("대표메뉴목록"),
                                fieldWithPath("data..bestMenus[].id").type(JsonFieldType.NUMBER).description("메뉴Id"),
                                fieldWithPath("data..bestMenus[].name").type(JsonFieldType.STRING).description("메뉴명"),
                                fieldWithPath("data..bestMenus[].imageUrl").type(JsonFieldType.NULL).description("메뉴썸네일"),
                                fieldWithPath("data..bestMenus[].best").type(JsonFieldType.BOOLEAN).description("대표메뉴여부"),
                                fieldWithPath("data..bestMenus[].soldOut").type(JsonFieldType.BOOLEAN).description("품절여부"),
                                fieldWithPath("data..bestMenus[].popular").type(JsonFieldType.BOOLEAN).description("인기메뉴여부"),
                                fieldWithPath("data..bestMenus[].price").type(JsonFieldType.NUMBER).description("메뉴가격"),
                                fieldWithPath("data..bestMenus[].menuOptions[]").type(JsonFieldType.ARRAY).description("메뉴옵션"),
                                fieldWithPath("data..bestMenus[].menuOptions[].optionId").type(JsonFieldType.NUMBER).description("메뉴옵션 Id"),
                                fieldWithPath("data..bestMenus[].menuOptions[].optionName").type(JsonFieldType.STRING).description("메뉴옵션명"),
                                fieldWithPath("data..bestMenus[].menuOptions[].optionPrice").type(JsonFieldType.NUMBER).description("메뉴옵션가격"),
                                fieldWithPath("data.menus").type(JsonFieldType.ARRAY).description("메뉴목록"),
                                fieldWithPath("data.menus[].id").type(JsonFieldType.NUMBER).description("메뉴Id"),
                                fieldWithPath("data.menus[].name").type(JsonFieldType.STRING).description("메뉴명"),
                                fieldWithPath("data.menus[].imageUrl").type(JsonFieldType.NULL).description("메뉴썸네일"),
                                fieldWithPath("data.menus[].best").type(JsonFieldType.BOOLEAN).description("대표메뉴여부"),
                                fieldWithPath("data.menus[].soldOut").type(JsonFieldType.BOOLEAN).description("품절여부"),
                                fieldWithPath("data.menus[].popular").type(JsonFieldType.BOOLEAN).description("인기메뉴여부"),
                                fieldWithPath("data.menus[].price").type(JsonFieldType.NUMBER).description("메뉴가격"),
                                fieldWithPath("data.menus[].menuOptions[]").type(JsonFieldType.ARRAY).description("메뉴옵션목록"),
                                fieldWithPath("data.menus[].menuOptions[].optionId").type(JsonFieldType.NUMBER).description("메뉴옵션 Id"),
                                fieldWithPath("data.menus[].menuOptions[].optionName").type(JsonFieldType.STRING).description("메뉴옵션명"),
                                fieldWithPath("data.menus[].menuOptions[].optionPrice").type(JsonFieldType.NUMBER).description("메뉴옵션가격")
                        )));
    }

    @Test
    @DisplayName("가게 이름으로 검색 테스트")
    void test_search() throws Exception {
        mockMvc.perform(get("/api/v1/bm/stores")
                .param("keyword", "동대문")
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
                .andDo(print())
                .andDo(document("store-search",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 사이즈"),
                                parameterWithName("keyword").description("검색어")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터").optional(),
                                fieldWithPath("data.totalCount").type(JsonFieldType.NUMBER).description("전체 데이터 개수"),
                                fieldWithPath("data.pageNumber").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                fieldWithPath("data.pageSize").type(JsonFieldType.NUMBER).description("한 페이지 크기"),
                                fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                                fieldWithPath("data.list").type(JsonFieldType.ARRAY).description("가게 목록"),
                                fieldWithPath("data.list[].storeId").type(JsonFieldType.NUMBER).description("가게 Id"),
                                fieldWithPath("data.list[].storeName").type(JsonFieldType.STRING).description("가게명"),
                                fieldWithPath("data.list[].storeIntro").type(JsonFieldType.STRING).description("가게소개"),
                                fieldWithPath("data.list[].storeImageUrl").type(JsonFieldType.NULL).description("가게 썸네일"),
                                fieldWithPath("data.list[].minOrderPrice").type(JsonFieldType.NUMBER).description("최소주문금액"),
                                fieldWithPath("data.list[].deliveryTip").type(JsonFieldType.NUMBER).description("배달팁"),
                                fieldWithPath("data.list[].minDeliveryTime").type(JsonFieldType.NUMBER).description("최소배달시간"),
                                fieldWithPath("data.list[].maxDeliveryTime").type(JsonFieldType.NUMBER).description("최대배달시간"),
                                fieldWithPath("data.list[].package").type(JsonFieldType.BOOLEAN).description("포장가능여부"),
                                fieldWithPath("data.list[].averageScore").type(JsonFieldType.NUMBER).description("평균평점"),
                                fieldWithPath("data.list[].totalReviewCount").type(JsonFieldType.NUMBER).description("전체 리뷰 수"),
                                fieldWithPath("data.list[].bestMenus").type(JsonFieldType.ARRAY).description("대표메뉴"),
                                fieldWithPath("data.list[].bestMenus[].id").type(JsonFieldType.NUMBER).description("메뉴Id"),
                                fieldWithPath("data.list[].bestMenus[].name").type(JsonFieldType.STRING).description("메뉴명"),
                                fieldWithPath("data.list[].bestMenus[].imageUrl").type(JsonFieldType.NULL).description("메뉴썸네일"),
                                fieldWithPath("data.list[].bestMenus[].best").type(JsonFieldType.BOOLEAN).description("대표메뉴여부"),
                                fieldWithPath("data.list[].bestMenus[].soldOut").type(JsonFieldType.BOOLEAN).description("품절여부"),
                                fieldWithPath("data.list[].bestMenus[].popular").type(JsonFieldType.BOOLEAN).description("인기메뉴여부"),
                                fieldWithPath("data.list[].bestMenus[].price").type(JsonFieldType.NUMBER).description("메뉴가격"),
                                fieldWithPath("data.list[].bestMenus[].menuOptions[]").type(JsonFieldType.ARRAY).description("메뉴옵션"),
                                fieldWithPath("data.list[].bestMenus[].menuOptions[].optionId").type(JsonFieldType.NUMBER).description("메뉴옵션 Id"),
                                fieldWithPath("data.list[].bestMenus[].menuOptions[].optionName").type(JsonFieldType.STRING).description("메뉴옵션명"),
                                fieldWithPath("data.list[].bestMenus[].menuOptions[].optionPrice").type(JsonFieldType.NUMBER).description("메뉴옵션가격")
                        )));
    }

}
