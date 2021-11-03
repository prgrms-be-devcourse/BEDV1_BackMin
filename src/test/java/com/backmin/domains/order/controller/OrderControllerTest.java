package com.backmin.domains.order.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.domain.MenuOption;
import com.backmin.domains.menu.domain.MenuOptionRepository;
import com.backmin.domains.menu.domain.MenuRepository;
import com.backmin.domains.menu.dto.request.MenuOptionReadParam;
import com.backmin.domains.menu.dto.request.MenuReadParam;
import com.backmin.domains.order.domain.Order;
import com.backmin.domains.order.domain.OrderRepository;
import com.backmin.domains.order.domain.OrderStatus;
import com.backmin.domains.order.domain.Payment;
import com.backmin.domains.order.dto.request.CreateOrderParam;
import com.backmin.domains.order.dto.request.UpdateOrderStatusParam;
import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.domain.CategoryRepository;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.domain.StoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class OrderControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    MenuOptionRepository menuOptionRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OrderRepository orderRepository;

    Store store;
    Member member;
    Menu menu;
    MenuOption menuOption;
    Member owner;

    @BeforeEach
    void setUp() {
        this.member = givenSaveMember();
        this.owner = givenSavedOwner();
        this.store = givenSavedStore(owner);
        this.menu = givenSavedMenu();
        this.menuOption = givenSavedMenuOption();
        this.menu.addMenuOption(menuOption);
        this.store.addMenu(menu);
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        storeRepository.deleteAll();
        memberRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("주문 저장 API 테스트")
    @Transactional
    void createOrder() throws Exception {
        CreateOrderParam createOrderParam = createRequest(store, member, menu, menuOption);

        mockMvc.perform(post("/api/v1/bm/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createOrderParam)))
                .andDo(print())
                .andDo(document("order-save",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 Id"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("requirement").type(JsonFieldType.STRING).description("요구 사항"),
                                fieldWithPath("storeId").type(JsonFieldType.NUMBER).description("가게 id"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
                                fieldWithPath("menuReadParams").type(JsonFieldType.ARRAY).description("메뉴"),
                                fieldWithPath("menuReadParams[].id").type(JsonFieldType.NUMBER).description("메뉴 Id"),
                                fieldWithPath("menuReadParams[].quantity").type(JsonFieldType.NUMBER).description("메뉴 주문 수량"),
                                fieldWithPath("menuReadParams[].menuOptionIds").type(JsonFieldType.ARRAY).description("메뉴 주문 옵션"),
                                fieldWithPath("payment").type(JsonFieldType.STRING).description("지불 방법")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));

        Order order = orderRepository.findAll().get(0);   // 떡복이2개 각각 당면추가
        assertThat(order.getAddress()).isEqualTo(createOrderParam.getAddress());
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
        assertThat(order.getPayMent()).isEqualTo(Payment.KAKAO_PAY);
        assertThat(order.getTotalPrice()).isEqualTo(29000);
    }

    @Test
    @DisplayName("[고객] 주문을 정상적으로 수정한다.")
    void updateOrderStatusByCustomer() throws Exception {
        Order order = Order.of("주소", "요구사항", Payment.KAKAO_PAY, member, store, 1000);
        Order savedOrder = orderRepository.save(order);

        UpdateOrderStatusParam request = new UpdateOrderStatusParam();
        request.setEmail("tester@email.com");
        request.setPassword("123456789a!");
        request.setOrderStatus(OrderStatus.CANCELED);
        request.setMemberId(member.getId());

        mockMvc.perform(post("/api/v1/bm/orders/{orderId}", savedOrder.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("order-status-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 Id"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("가게 상태")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));

        Order updatedOrder = orderRepository.findById(savedOrder.getId()).get();
        assertThat(updatedOrder.getStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    @Test
    @DisplayName("[고객] 고객은 주문 수락을 할 수 없다.")
    void not_updateOrderStatusToOkByMember() throws Exception {
        Order order = Order.of("주소", "요구사항", Payment.KAKAO_PAY, member, store, 1000);

        Order savedOrder = orderRepository.save(order);

        UpdateOrderStatusParam request = new UpdateOrderStatusParam();
        request.setEmail("tester@email.com");
        request.setPassword("123456789a!");
        request.setOrderStatus(OrderStatus.DELIVERED);
        request.setMemberId(member.getId());

        mockMvc.perform(post("/api/v1/bm/orders/{orderId}", savedOrder.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        assertThat(savedOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @Test
    @DisplayName("[가게 사장] 가게 주인은 주문을 수락할 수 있다.")
    void updateOrderStatusToOkByOwner() throws Exception {
        Order order = Order.of("주소", "요구사항", Payment.KAKAO_PAY, member, store, 1000);

        Order savedOrder = orderRepository.save(order);

        UpdateOrderStatusParam request = new UpdateOrderStatusParam();
        request.setEmail("owner@email.com");
        request.setPassword("123456789a!");
        request.setOrderStatus(OrderStatus.DELIVERED);
        request.setMemberId(owner.getId());

        mockMvc.perform(post("/api/v1/bm/orders/{orderId}", savedOrder.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("data").doesNotExist())
                .andExpect(jsonPath("serverDatetime").isString());

        Order updatedOrder = orderRepository.findById(savedOrder.getId()).get();
        assertThat(updatedOrder.getStatus()).isEqualTo(OrderStatus.DELIVERED);
    }

    @Test
    @DisplayName("주문 내역 상세 조회")
    void getOrdersByMemberId() throws Exception {
        Order order = Order.of("주소", "요청사항", Payment.KAKAO_PAY, member, store, 1000);
        IntStream.range(1, 10).forEach(i -> orderRepository.save(order));

        mockMvc.perform(get("/api/v1/bm/orders/members/{memberId}", member.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("orders-page",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("page").description("page"),
                                parameterWithName("size").description("size")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.totalCount").type(JsonFieldType.NUMBER).description("응답 데이터"),
                                fieldWithPath("data.pageNumber").type(JsonFieldType.NUMBER).description("응답 데이터"),
                                fieldWithPath("data.pageSize").type(JsonFieldType.NUMBER).description("응답 데이터"),
                                fieldWithPath("data.list").type(JsonFieldType.ARRAY).description("응답 데이터"),
                                fieldWithPath("data.list[].orderId").type(JsonFieldType.NUMBER).description("응답 데이터"),
                                fieldWithPath("data.list[].storeId").type(JsonFieldType.NUMBER).description("응답 데이터"),
                                fieldWithPath("data.list[].storeName").type(JsonFieldType.STRING).description("응답 데이터"),
                                fieldWithPath("data.list[].menuReadResponses").type(JsonFieldType.ARRAY).description("응답 데이터"),
                                fieldWithPath("data.list[].orderDateTime").type(JsonFieldType.STRING).description("응답 데이터"),
                                fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("응답 데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    private Member givenSaveMember() {
        return memberRepository.save(Member.builder()
                .nickName("tester")
                .email("tester@email.com")
                .password("123456789a!")
                .address("서울시")
                .phoneNumber("010-1111-3333")
                .build());
    }

    private Category givenSavedCategory() {
        return categoryRepository.save(Category.builder().name("떡볶이").build());
    }

    private Menu givenSavedMenu() {
        return menuRepository.save(Menu.builder()
                .name("떡볶이")
                .price(12000)
                .description("기본 떡볶이")
                .build());
    }

    private MenuOption givenSavedMenuOption() {
        return menuOptionRepository.save(MenuOption.builder()
                .id(111L)
                .name("당면추가")
                .price(1000)
                .build());
    }

    private CreateOrderParam createRequest(Store saveStore, Member saveMember, Menu saveMenu, MenuOption saveMenuOption) {
        MenuOptionReadParam menuOptionDto = new MenuOptionReadParam();
        menuOptionDto.setId(saveMenuOption.getId());

        List<Long> menuOptionDtos = new ArrayList<>();
        menuOptionDtos.add(menuOptionDto.getId());

        MenuReadParam menuReadParam = new MenuReadParam();
        menuReadParam.setId(saveMenu.getId());
        menuReadParam.setQuantity(2);
        menuReadParam.setMenuOptionIds(menuOptionDtos);

        List<MenuReadParam> menuReadParams = new ArrayList<>();
        menuReadParams.add(menuReadParam);

        CreateOrderParam createOrderParam = new CreateOrderParam();
        createOrderParam.setAddress("서울시 건대");
        createOrderParam.setMemberId(saveMember.getId());
        createOrderParam.setRequirement("요구사항");
        createOrderParam.setPayment(Payment.KAKAO_PAY);
        createOrderParam.setPassword("123456789a!");
        createOrderParam.setStoreId(saveStore.getId());
        createOrderParam.setMenuReadParams(menuReadParams);
        return createOrderParam;
    }

    private Store givenSavedStore(Member owner) {
        Store store = Store.builder()
                .name("동대문 엽기 떡볶이")
                .deliveryTip(1000)
                .phoneNumber("010-1111-2222")
                .minOrderPrice(10000)
                .minDeliveryTime(60)
                .maxDeliveryTime(120)
                .storeIntro("매콤 매콤 떡볶이!")
                .deliveryTip(3000)
                .category(givenSavedCategory())
                .member(owner)
                .build();
        return storeRepository.save(store);
    }

    private Member givenSavedOwner() {
        Member owner = Member.builder()
                .nickName("owner")
                .phoneNumber("111")
                .address("주소")
                .email("owner@email.com")
                .password("123456789a!")
                .build();
        return memberRepository.save(owner);
    }
}
