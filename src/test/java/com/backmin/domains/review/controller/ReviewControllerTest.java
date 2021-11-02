package com.backmin.domains.review.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.order.domain.Order;
import com.backmin.domains.order.domain.OrderRepository;
import com.backmin.domains.order.domain.Payment;
import com.backmin.domains.review.converter.ReviewConverter;
import com.backmin.domains.review.domain.Review;
import com.backmin.domains.review.domain.ReviewRepository;
import com.backmin.domains.review.domain.dto.request.ReviewCreateParam;
import com.backmin.domains.review.service.ReviewService;
import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.domain.CategoryRepository;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.domain.StoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewController reviewController;

    @Autowired
    ReviewConverter reviewConverter;

    @Autowired
    ReviewService reviewService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    private Store givenStore() {
        Store store = Store.builder()
                .name("동대문 엽기 떡볶이")
                .deliveryTip(1000)
                .phoneNumber("010-1111-2222")
                .minOrderPrice(10000)
                .minDeliveryTime(60)
                .maxDeliveryTime(120)
                .storeIntro("매콤 매콤 떡볶이!")
                .deliveryTip(3000)
                .category(saveCategory())
                .build();
        return storeRepository.save(store);
    }

    private Member givenMember() {
        Member member = Member.builder()
                .id(1L)
                .email("customer@gmail.com")
                .nickName("이구역개발왕")
                .password("1234")
                .phoneNumber("010-1234-5678")
                .address("서울 송파구 올림픽로 300 롯데월드타워 101층")
                .build();
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("리뷰 저장 테스트")
    @Transactional
    void create_Review() throws Exception {
        Member member = Member.builder()
                .nickName("hello")
                .phoneNumber("111")
                .address("주소")
                .email("email12@email.com")
                .password("password")
                .build();
        Member saveMember = memberRepository.save(member);

        Store store = Store.builder()
                .name("동대문 엽기 떡볶이")
                .deliveryTip(1000)
                .phoneNumber("010-1111-2222")
                .minOrderPrice(10000)
                .minDeliveryTime(60)
                .maxDeliveryTime(120)
                .storeIntro("매콤 매콤 떡볶이!")
                .deliveryTip(3000)
                .category(saveCategory())
                .member(member)
                .build();
        Store saveStore = storeRepository.save(store);

        Order order = Order.of("주소", "요청사항", Payment.KAKAO_PAY, member, saveStore, 1000);
        IntStream.range(1, 10).forEach(i -> orderRepository.save(order));

        ReviewCreateParam reviewCreateParam = ReviewCreateParam.of(2L,
                store,
                5,
                member,
                order,
                "게임에 참가를 원하십니까?"
        );

        mockMvc.perform(post("/api/v1/bm/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewCreateParam)))
                .andDo(print())
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("data").doesNotExist());

        assertThat(reviewCreateParam.getStore().getName()).isEqualTo("동대문 엽기 떡볶이");
    }

    @Test
    @DisplayName("가게의 리뷰를 모두 조회한다.")
    void findStoreReviews() throws Exception {
        Member member = Member.builder()
                .nickName("나부자")
                .phoneNumber("112")
                .address("서울시 송파구 올림픽로 300 롯데월드ㅌ워 101층")
                .email("thisisemail@email.com")
                .password("passwordispassword")
                .build();
        Member saveMember = memberRepository.save(member);

        Store store = Store.builder()
                .name("이것저것 다팔아 상점")
                .deliveryTip(1000)
                .phoneNumber("010-1111-2222")
                .minOrderPrice(10000)
                .minDeliveryTime(60)
                .maxDeliveryTime(120)
                .storeIntro("무엇이든 만들어드립니다!")
                .deliveryTip(3000)
                .category(saveCategory())
                .member(member)
                .build();
        Store saveStore = storeRepository.save(store);

        Order order = Order.of("주소", "요청사항", Payment.KAKAO_PAY, member, saveStore, 1000);
        IntStream.range(1, 10).forEach(i -> orderRepository.save(order));

        Order order1 = Order.of(member.getAddress(), "양 많이요,", Payment.KAKAO_PAY, member, 2000);
        Order order2 = Order.of(member.getAddress(), "양 많이요,", Payment.KAKAO_PAY, member, 2000);
        Order order3 = Order.of(member.getAddress(), "양 많이요,", Payment.KAKAO_PAY, member, 2000);
        Order order4 = Order.of(member.getAddress(), "양 많이요,", Payment.KAKAO_PAY, member, 2000);
        Order order5 = Order.of(member.getAddress(), "양 많이요,", Payment.KAKAO_PAY, member, 2000);
        List<Order> orders = List.of(order1, order2, order3, order4, order5);
        orderRepository.saveAll(orders);

        Review review1 = Review.of(
                saveStore,
                1,
                member,
                order1,
                "이집은 정말 최고로 맛있습니다.11"
        );

        Review review2 = Review.of(
                saveStore,
                2,
                member,
                order2,
                "치킨이 아주 맛있습니다.22"
        );
        Review review3 = Review.of(
                saveStore,
                3,
                member,
                order3,
                "스파게티가 정말 맛있습니다.33"
        );
        Review review4 = Review.of(
                saveStore,
                4,
                member,
                order4,
                "피자의 치즈가 쭉쭉 늘어나서 맛있습니다.44"
        );
        Review review5 = Review.of(
                saveStore,
                5,
                member,
                order5,
                "면이 불지 않아 맛있습니다.55"
        );
        List<Review> reviews = List.of(review1, review2, review3, review4, review5);
        reviewRepository.saveAll(reviews);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/bm/reviews/store/{store_id}", saveStore.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10)))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8"));
        System.out.println(content);
    }

    private Category saveCategory() {
        return categoryRepository.save(Category.builder().name("떡볶이").build());
    }

}