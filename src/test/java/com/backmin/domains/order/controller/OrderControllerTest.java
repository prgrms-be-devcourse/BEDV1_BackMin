package com.backmin.domains.order.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.domain.MenuOption;
import com.backmin.domains.menu.domain.MenuOptionRepository;
import com.backmin.domains.menu.domain.MenuRepository;
import com.backmin.domains.menu.dto.MenuDto;
import com.backmin.domains.menu.dto.MenuOptionDto;
import com.backmin.domains.order.domain.Order;
import com.backmin.domains.order.domain.OrderRepository;
import com.backmin.domains.order.domain.OrderStatus;
import com.backmin.domains.order.domain.Payment;
import com.backmin.domains.order.dto.OrderCreateRequest;
import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.domain.CategoryRepository;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.domain.StoreRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

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
    void createOrder() throws Exception {
        Member saveMember = saveMember();
        Category saveCategory = saveCategory();
        Menu saveMenu = saveMenu();
        MenuOption saveMenuOption = saveMenuOption();
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
                .category(saveCategory)
                .build();

        saveMenu.addMenuOption(saveMenuOption);
        store.addMenu(saveMenu);
        Store saveStore = storeRepository.save(store);

        MenuOptionDto menuOptionDto = new MenuOptionDto();
        menuOptionDto.setId(saveMenuOption.getId());
        menuOptionDto.setQuantity(1);

        List<MenuOptionDto> menuOptionDtos = new ArrayList<>();
        menuOptionDtos.add(menuOptionDto);

        MenuDto menuDto = new MenuDto();
        menuDto.setId(saveMenu.getId());
        menuDto.setQuantity(2);
        menuDto.setMenuOptionDtos(menuOptionDtos);

        List<MenuDto> menuDtos = new ArrayList<>();
        menuDtos.add(menuDto);

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setAddress("서울시 건대");
        orderCreateRequest.setMemberId(saveMember.getId());
        orderCreateRequest.setRequirement("요구사항");
        orderCreateRequest.setPayment(Payment.KAKAO_PAY);
        orderCreateRequest.setPassword("123456789a!");
        orderCreateRequest.setStoreId(saveStore.getId());
        orderCreateRequest.setMenuDtos(menuDtos);

        mockMvc.perform(post("/api/v1/bm/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(orderCreateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("data").doesNotExist())
                .andExpect(jsonPath("serverDatetime").isString());

        Order order = orderRepository.findAll().get(0);
        assertThat(order.getAddress()).isEqualTo(orderCreateRequest.getAddress());
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
        assertThat(order.getPayMent()).isEqualTo(Payment.KAKAO_PAY);
        assertThat(order.getTotalPrice()).isEqualTo(31000);
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

}