package com.backmin.domains.review.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.order.domain.Order;
import com.backmin.domains.order.domain.OrderRepository;
import com.backmin.domains.order.domain.Payment;
import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.domain.CategoryRepository;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.domain.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DisplayName("ReviewRepository 테스트")
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    void tearDown() {
        reviewRepository.deleteAll();
        orderRepository.deleteAll();
        storeRepository.deleteAll();
        memberRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("가게별 총 리뷰 개수 조회 테스트")
    void getReviewTotalCountByStore() {

        // given
        Category category1 = Category.builder()
                .name("한식")
                .build();
        Category savedCategory = categoryRepository.save(category1);

        Member owner1 = Member.of("owner111@gmail.com",
                "12345",
                "010-1112-2222",
                "야이야이야",
                "인천광역시"
        );
        Member savedOwner = memberRepository.save(owner1);

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
                savedCategory,
                savedOwner,
                new ArrayList<>() // menus
        );
        Store savedStore = storeRepository.save(store1);

        Member member = Member.builder()
                .email("devrunner21@gmail.com")
                .address("수원시")
                .nickName("K")
                .password("1234")
                .phoneNumber("01023564756")
                .build();
        memberRepository.save(member);

        Order order1 = Order.of(member.getAddress(), "양 많이요,", Payment.KAKAO_PAY, member, 2000);
        Order order2 = Order.of(member.getAddress(), "양 많이요,", Payment.KAKAO_PAY, member, 2000);
        Order order3 = Order.of(member.getAddress(), "양 많이요,", Payment.KAKAO_PAY, member, 2000);
        List<Order> orders = List.of(order1, order2, order3);
        orderRepository.saveAll(orders);

        Review review1 = Review.of(
                savedStore,
                1,
                member,
                order1,
                "맛있습니다."
        );

        Review review2 = Review.of(
                savedStore,
                2,
                member,
                order2,
                "맛있습니다.22"
        );
        Review review3 = Review.of(
                savedStore,
                3,
                member,
                order3,
                "맛있습니다.33"
        );

        List<Review> reviews = List.of(review1, review2, review3);
        reviewRepository.saveAll(reviews);

        // when
        int reviewTotalCountByStore = reviewRepository.getReviewTotalCountByStore(savedStore.getId());

        // then
        assertThat(reviewTotalCountByStore, is(reviews.size()));
    }

    @Test
    @DisplayName("가게별 평균 평점 조회 테스트")
    void getReviewStoreAverageByStore() {

        // given
        Category category1 = Category.builder()
                .name("한식")
                .build();
        Category savedCategory = categoryRepository.save(category1);

        Member owner1 = Member.of("owner111@gmail.com",
                "12345",
                "010-1112-2222",
                "야이야이야",
                "인천광역시"
        );
        Member savedOwner = memberRepository.save(owner1);

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
                savedCategory,
                savedOwner
        );
        Store savedStore = storeRepository.save(store1);

        Member member = Member.builder()
                .email("devrunner21@gmail.com")
                .address("수원시")
                .nickName("K")
                .password("1234")
                .phoneNumber("01023564756")
                .build();
        memberRepository.save(member);

        Order order1 = Order.of(member.getAddress(), "양 많이요,", Payment.KAKAO_PAY, member, 2000);
        Order order2 = Order.of(member.getAddress(), "양 많이요,", Payment.KAKAO_PAY, member, 2000);
        Order order3 = Order.of(member.getAddress(), "양 많이요,", Payment.KAKAO_PAY, member, 2000);
        Order order4 = Order.of(member.getAddress(), "양 많이요,", Payment.KAKAO_PAY, member, 2000);
        Order order5 = Order.of(member.getAddress(), "양 많이요,", Payment.KAKAO_PAY, member, 2000);
        List<Order> orders = List.of(order1, order2, order3, order4, order5);
        orderRepository.saveAll(orders);

        Review review1 = Review.of(
                savedStore,
                1,
                member,
                order1,
                "맛있습니다."
        );

        Review review2 = Review.of(
                savedStore,
                2,
                member,
                order2,
                "맛있습니다.22"
        );
        Review review3 = Review.of(
                savedStore,
                3,
                member,
                order3,
                "맛있습니다.33"
        );
        Review review4 = Review.of(
                savedStore,
                4,
                member,
                order4,
                "맛있습니다.44"
        );
        Review review5 = Review.of(
                savedStore,
                5,
                member,
                order5,
                "맛있습니다.55"
        );
        List<Review> reviews = List.of(review1, review2, review3, review4, review5);
        reviewRepository.saveAll(reviews);

        // when
        double reviewStoreAverageByStore = reviewRepository.getReviewAverageByStore(savedStore.getId());

        // then
        double calculatedAverage = reviews.stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0);
        assertThat(reviewStoreAverageByStore, is(calculatedAverage));
    }

}
