package com.backmin.domains.order.controller;

import com.backmin.domains.common.dto.ApiResult;
import com.backmin.domains.common.dto.PageResult;
import com.backmin.domains.common.enums.ErrorInfo;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.member.dto.response.MemberOrderPageResult;
import com.backmin.domains.member.service.MemberService;
import com.backmin.domains.order.dto.request.CreateOrderParam;
import com.backmin.domains.order.dto.request.UpdateOrderStatusParam;
import com.backmin.domains.order.service.OrderService;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.domain.StoreRepository;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/bm/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult createOrder(@RequestBody @Valid CreateOrderParam createOrderParam) {
        orderService.saveOrder(createOrderParam);
        return ApiResult.builder().success(true).build();
    }

    @PostMapping(path = "/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult updateOrderStatus(@PathVariable Long orderId, @RequestBody @Valid UpdateOrderStatusParam request) {
        boolean isAuthentication = memberService.authenticateMember(request.getMemberId(), request.getEmail(), request.getPassword());
        if (isAuthentication) {
            orderService.editOrderStatus(orderId, request.getMemberId(), request.getOrderStatus());
            return ApiResult.ok();
        }
        return ApiResult.error(ErrorInfo.MEMBER_NOT_FOUND.getCode(), ErrorInfo.MEMBER_NOT_FOUND.getMessage());
    }

    @GetMapping(path = "/members/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<PageResult<MemberOrderPageResult>> getMemberOrders(@PathVariable Long memberId, Pageable pageRequest) {
        return ApiResult.ok(orderService.getOrdersByMember(memberId, pageRequest));
    }

}
