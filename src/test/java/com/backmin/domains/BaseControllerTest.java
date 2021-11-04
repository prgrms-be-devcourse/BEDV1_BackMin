package com.backmin.domains;

import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.menu.domain.MenuOptionRepository;
import com.backmin.domains.menu.domain.MenuRepository;
import com.backmin.domains.order.domain.OrderRepository;
import com.backmin.domains.review.controller.ReviewController;
import com.backmin.domains.review.converter.ReviewConverter;
import com.backmin.domains.review.domain.ReviewRepository;
import com.backmin.domains.review.service.ReviewService;
import com.backmin.domains.store.domain.CategoryRepository;
import com.backmin.domains.store.domain.StoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Disabled
public class BaseControllerTest {

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected StoreRepository storeRepository;

    @Autowired
    protected MenuRepository menuRepository;

    @Autowired
    protected MenuOptionRepository menuOptionRepository;

    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected ReviewController reviewController;

    @Autowired
    protected ReviewConverter reviewConverter;

    @Autowired
    protected ReviewService reviewService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        reviewRepository.deleteAll();
        orderRepository.deleteAll();
        storeRepository.deleteAll();
        memberRepository.deleteAll();
        categoryRepository.deleteAll();
    }

}
