package com.backmin.domains.order.controller;

import com.backmin.domains.common.dto.ApiResult;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.order.dto.OrderCreateRequest;
import com.backmin.domains.order.service.OrderService;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.domain.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/bm/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @PostMapping
    public ApiResult createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        Member findMember = memberRepository.findById(orderCreateRequest.getMemberId()).get();
        Store store = storeRepository.findById(orderCreateRequest.getStoreId()).get();

        orderService.saveOrder(orderCreateRequest, findMember, store);
        return ApiResult.builder().success(true).build();
    }

}
