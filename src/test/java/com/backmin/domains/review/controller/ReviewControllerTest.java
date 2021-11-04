package com.backmin.domains.review.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.backmin.domains.BaseControllerTest;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.order.domain.Order;
import com.backmin.domains.order.domain.Payment;
import com.backmin.domains.review.domain.Review;
import com.backmin.domains.review.domain.dto.request.ReviewCreateParam;
import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.domain.Store;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.transaction.annotation.Transactional;

class ReviewControllerTest extends BaseControllerTest {

    @Test
    @DisplayName("리뷰 등록 API 테스트")
    @Transactional
    void create_Review() throws Exception {
        Member member = Member.builder()
                .nickName("hello")
                .phoneNumber("010-1234-5671")
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

        ReviewCreateParam reviewCreateParam = new ReviewCreateParam();
        reviewCreateParam.setStoreId(saveStore.getId());
        reviewCreateParam.setScore(5);
        reviewCreateParam.setMemberId(saveMember.getId());
        reviewCreateParam.setNickName(saveMember.getNickName());
        reviewCreateParam.setOrderId(order.getId());
        reviewCreateParam.setContent("게임에 참가를 원하십니까?");

        mockMvc.perform(post("/api/v1/bm/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewCreateParam)))
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("data").doesNotExist())
                .andDo(print())
                .andDo(document("review-save",
                        requestFields(
                                fieldWithPath("storeId").type(JsonFieldType.NUMBER).description("storeId"),
                                fieldWithPath("score").type(JsonFieldType.NUMBER).description("score"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("memberId"),
                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("nickName"),
                                fieldWithPath("orderId").type(JsonFieldType.NUMBER).description("orderId"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("createdAt").type(JsonFieldType.NULL).description("createdAt"),
                                fieldWithPath("updatedAt").type(JsonFieldType.NULL).description("updatedAt")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")

                        )));

    }

    @Test
    @DisplayName("가게 리뷰 조회 API 테스트")
    @Transactional
    void findStoreReviews() throws Exception {
        Member member = Member.builder()
                .nickName("나부자")
                .phoneNumber("010-1111-1112")
                .address("서울시 송파구 올림픽로 300 롯데월드타워 101층")
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

        mockMvc.perform(get("/api/v1/bm/reviews/store/{store_id}", saveStore.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10)))
                .andDo(print())
                .andDo(document("review-save",
                        requestParameters(
                                parameterWithName("page").description("페이지번호"),
                                parameterWithName("size").description("페이지 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.totalCount").type(JsonFieldType.NUMBER).description("totalCount"),
                                fieldWithPath("data.pageNumber").type(JsonFieldType.NUMBER).description("pageNumber"),
                                fieldWithPath("data.pageSize").type(JsonFieldType.NUMBER).description("pageSize"),
                                fieldWithPath("data.list").type(JsonFieldType.ARRAY).description("list"),
                                fieldWithPath("data.list[].reviewId").type(JsonFieldType.NUMBER).description("list[].reviewId"),
                                fieldWithPath("data.list[].storeId").type(JsonFieldType.NUMBER).description("list[].storeId"),
                                fieldWithPath("data.list[].score").type(JsonFieldType.NUMBER).description("list[].score"),
                                fieldWithPath("data.list[].memberId").type(JsonFieldType.NUMBER).description("list[].memberId"),
                                fieldWithPath("data.list[].nickName").type(JsonFieldType.STRING).description("list[].nickName"),
                                fieldWithPath("data.list[].orderId").type(JsonFieldType.NUMBER).description("list[].orderId"),
                                fieldWithPath("data.list[].content").type(JsonFieldType.STRING).description("list[].content"),
                                fieldWithPath("data.list[].createdAt").type(JsonFieldType.STRING).description("list[].createdAt"),
                                fieldWithPath("data.list[].updatedAt").type(JsonFieldType.STRING).description("list[].updatedAt"),
                                fieldWithPath("data.list[].reviewId").type(JsonFieldType.NUMBER).description("list[].reviewId"),
                                fieldWithPath("data.list[].storeId").type(JsonFieldType.NUMBER).description("list[].storeId"),
                                fieldWithPath("data.list[].score").type(JsonFieldType.NUMBER).description("list[].score"),
                                fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("hasNext"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )));
    }


    private Category saveCategory() {
        return categoryRepository.save(Category.builder().name("떡볶이").build());
    }

}