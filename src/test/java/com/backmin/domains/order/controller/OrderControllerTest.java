package com.backmin.domains.order.controller;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.backmin.domains.order.dto.OrderCreateRequest;
import com.backmin.domains.order.dto.UpdateOrderStatusRequest;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
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

    @BeforeEach
    void setUp() {
        this.store = givenSavedStore();
        this.member = givenSaveMember();
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
    }

    @Test
    @DisplayName("주문 저장 API 테스트")
    @Transactional
    void createOrder() throws Exception {
        OrderCreateRequest orderCreateRequest = createRequest(store, member, menu, menuOption);

        mockMvc.perform(post("/api/v1/bm/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(orderCreateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("data").doesNotExist())
                .andExpect(jsonPath("serverDatetime").isString());

        Order order = orderRepository.findAll().get(0);   // 떡복이2개 각각 당면추가
        assertThat(order.getAddress()).isEqualTo(orderCreateRequest.getAddress());
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
        assertThat(order.getPayMent()).isEqualTo(Payment.KAKAO_PAY);
        assertThat(order.getTotalPrice()).isEqualTo(29000);
    }

    @Test
    @DisplayName("[고객] 주문을 정상적으로 수정한다.")
    void updateOrderStatusByCustomer() throws Exception {
        Order order = Order.of("주소", "요구사항", Payment.KAKAO_PAY, member, store, 1000);
        Order savedOrder = orderRepository.save(order);

        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setEmail("tester@email.com");
        request.setPassword("123456789a!");
        request.setOrderStatus(OrderStatus.CANCELED);
        request.setMemberId(member.getId());

        mockMvc.perform(post("/api/v1/bm/orders/{orderId}", savedOrder.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("data").doesNotExist())
                .andExpect(jsonPath("serverDatetime").isString());

        Order updatedOrder = orderRepository.findById(savedOrder.getId()).get();
        assertThat(updatedOrder.getStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    @Test
    @DisplayName("[고객] 고객은 주문 수락을 할 수 없다.")
    void updateOrderStatusToOkByMember() throws Exception {
        Order order = Order.of("주소", "요구사항", Payment.KAKAO_PAY, member, store, 1000);

        Order savedOrder = orderRepository.save(order);

        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
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
                .andExpect(jsonPath("data.totalCount").exists())
                .andExpect(jsonPath("data.pageNumber").exists())
                .andExpect(jsonPath("data.pageSize").exists())
                .andExpect(jsonPath("data.list").exists());
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

    private OrderCreateRequest createRequest(Store saveStore, Member saveMember, Menu saveMenu, MenuOption saveMenuOption) {
        MenuOptionReadParam menuOptionDto = new MenuOptionReadParam();
        menuOptionDto.setId(saveMenuOption.getId());

        List<Long> menuOptionDtos = new ArrayList<>();
        menuOptionDtos.add(menuOptionDto.getId());

        MenuReadParam menuReadParam = new MenuReadParam();
        menuReadParam.setId(saveMenu.getId());
        menuReadParam.setQuantity(2);
        menuReadParam.setMenuOptionId(menuOptionDtos);

        List<MenuReadParam> menuReadParams = new ArrayList<>();
        menuReadParams.add(menuReadParam);

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setAddress("서울시 건대");
        orderCreateRequest.setMemberId(saveMember.getId());
        orderCreateRequest.setRequirement("요구사항");
        orderCreateRequest.setPayment(Payment.KAKAO_PAY);
        orderCreateRequest.setPassword("123456789a!");
        orderCreateRequest.setStoreId(saveStore.getId());
        orderCreateRequest.setMenuReadParams(menuReadParams);
        return orderCreateRequest;
    }

    private Store givenSavedStore() {
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
                .member(givenSavedOwner())
                .build();
        return storeRepository.save(store);
    }

    private Member givenSavedOwner() {
        Member owner = Member.builder()
                .nickName("owner")
                .phoneNumber("111")
                .address("주소")
                .email("email@email.com")
                .password("password")
                .build();
        return memberRepository.save(owner);
    }
}
