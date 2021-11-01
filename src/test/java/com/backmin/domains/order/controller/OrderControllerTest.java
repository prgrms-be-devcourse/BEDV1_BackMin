package com.backmin.domains.order.controller;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.backmin.domains.menu.dto.MenuOptionReadRequest;
import com.backmin.domains.menu.dto.MenuReadRequest;
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

    @Test
    @DisplayName("주문 저장 API 테스트")
    @Transactional
    void createOrder() throws Exception {
        Store store = Store.builder()
                .name("동대문 엽기 떡볶이")
                .deliveryTip(1000)
                .phoneNumber("010-1111-2222")
                .minOrderPrice(10000)
                .minDeliveryTime(60)
                .maxDeliveryTime(120)
                .mainIntro("메인 소개")
                .storeIntro("매콤 매콤 떡볶이!")
                .deliveryTip(3000)
                .category(saveCategory())
                .build();
        Store saveStore = storeRepository.save(store);

        Member saveMember = saveMember();
        Menu saveMenu = saveMenu();
        MenuOption saveMenuOption = saveMenuOption();

        saveMenu.addMenuOption(saveMenuOption);
        saveStore.addMenu(saveMenu);

        OrderCreateRequest orderCreateRequest = createRequest(saveStore, saveMember, saveMenu, saveMenuOption);

        mockMvc.perform(post("/api/v1/bm/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(orderCreateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("data").doesNotExist())
                .andExpect(jsonPath("serverDatetime").isString());

        Order order = orderRepository.findAll().get(1);   // 떡복이2개 각각 당면추가
        assertThat(order.getAddress()).isEqualTo(orderCreateRequest.getAddress());
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
        assertThat(order.getPayMent()).isEqualTo(Payment.KAKAO_PAY);
        assertThat(order.getTotalPrice()).isEqualTo(29000);
    }

    @Test
    @DisplayName("[고객] 주문을 정상적으로 수정한다.")
    void updateOrderStatusByCustomer() throws Exception {
        Store saveStore = setUpAndGivenStore();
        Member member = saveMember();

        Order order = Order.of("주소", "요구사항", Payment.KAKAO_PAY, member, saveStore,1000);

        orderRepository.save(order);

        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setEmail("tester@email.com");
        request.setPassword("123456789a!");
        request.setOrderStatus(OrderStatus.CANCELED);
        request.setMemberId(member.getId());

        mockMvc.perform(post("/api/v1/bm/orders/{orderId}", order.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("data").doesNotExist())
                .andExpect(jsonPath("serverDatetime").isString());

        Order saveOrder = orderRepository.findById(order.getId()).get();

        assertThat(saveOrder.getStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    @Test
    @DisplayName("[고객] 고객은 주문 수락을 할 수 없다.")
    void updateOrderStatusToOkByMember() throws Exception {
        Store saveStore = setUpAndGivenStore();
        Member member = saveMember();

        Order order = Order.of("주소", "요구사항", Payment.KAKAO_PAY, member, saveStore,1000);

        orderRepository.save(order);

        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setEmail("tester@email.com");
        request.setPassword("123456789a!");
        request.setOrderStatus(OrderStatus.DELIVERED);
        request.setMemberId(member.getId());

        mockMvc.perform(post("/api/v1/bm/orders/{orderId}", order.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("serverDatetime").isString());

        Order saveOrder = orderRepository.findById(order.getId()).get();

        assertThat(saveOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    private Member saveMember() {
        return memberRepository.save(Member.builder()
                .nickName("tester")
                .email("tester@email.com")
                .password("123456789a!")
                .address("서울시")
                .phoneNumber("010-1111-3333")
                .build());
    }

    private Category saveCategory() {
        return categoryRepository.save(Category.builder().name("떡볶이").build());
    }

    private Menu saveMenu() {
        return menuRepository.save(Menu.builder()
                .name("떡볶이")
                .price(12000)
                .description("기본 떡볶이")
                .build());
    }

    private MenuOption saveMenuOption() {
        return menuOptionRepository.save(MenuOption.builder()
                .id(111L)
                .name("당면추가")
                .price(1000)
                .build());
    }

    private OrderCreateRequest createRequest(Store saveStore, Member saveMember, Menu saveMenu, MenuOption saveMenuOption) {
        MenuOptionReadRequest menuOptionDto = new MenuOptionReadRequest();
        menuOptionDto.setId(saveMenuOption.getId());

        List<Long> menuOptionDtos = new ArrayList<>();
        menuOptionDtos.add(menuOptionDto.getId());

        MenuReadRequest menuReadRequest = new MenuReadRequest();
        menuReadRequest.setId(saveMenu.getId());
        menuReadRequest.setQuantity(2);
        menuReadRequest.setMenuOptionId(menuOptionDtos);

        List<MenuReadRequest> menuReadRequests = new ArrayList<>();
        menuReadRequests.add(menuReadRequest);

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setAddress("서울시 건대");
        orderCreateRequest.setMemberId(saveMember.getId());
        orderCreateRequest.setRequirement("요구사항");
        orderCreateRequest.setPayment(Payment.KAKAO_PAY);
        orderCreateRequest.setPassword("123456789a!");
        orderCreateRequest.setStoreId(saveStore.getId());
        orderCreateRequest.setMenuReadRequests(menuReadRequests);
        return orderCreateRequest;
    }

    private Store setUpAndGivenStore() {
        Member saveOwner = givenOwner();
        Store store = Store.builder()
                .name("동대문 엽기 떡볶이")
                .deliveryTip(1000)
                .phoneNumber("010-1111-2222")
                .minOrderPrice(10000)
                .minDeliveryTime(60)
                .maxDeliveryTime(120)
                .mainIntro("메인 소개")
                .storeIntro("매콤 매콤 떡볶이!")
                .deliveryTip(3000)
                .category(saveCategory())
                .member(saveOwner)
                .build();
        return storeRepository.save(store);
    }

    private Member givenOwner() {
        Member owner = Member.builder()
                .nickName("owner")
                .phoneNumber("111")
                .address("주소")
                .email("email@email.com")
                .password("password")
                .build();
        Member saveOwner = memberRepository.save(owner);
        return saveOwner;
    }
}