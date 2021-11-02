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
@RequestMapping(path = "/api/v1/bm/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;

    @PostMapping
    public ApiResult createOrder(@RequestBody CreateOrderParam createOrderParam) {
        orderService.saveOrder(createOrderParam);
        return ApiResult.builder().success(true).build();
    }

    @PostMapping("/{orderId}")
    public ApiResult updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusParam request) {
        boolean isAuthentication = memberService.authenticateMember(request.getMemberId(), request.getEmail(), request.getPassword());
        /**
         * 추후에 security가 생기면 if문은 aop나 다른 방법으로 사용되어 변경 될 코드
         * 51 ~ 53번 라인이 남아있을 코드
         */
        if (isAuthentication) {
            orderService.editOrderStatus(orderId, request.getMemberId(), request.getOrderStatus());
            return ApiResult.ok();
        }
        return ApiResult.error(ErrorInfo.MEMBER_NOT_FOUND.getCode(), ErrorInfo.MEMBER_NOT_FOUND.getMessage());
    }

    @GetMapping("/members/{memberId}")
    public ApiResult<PageResult<MemberOrderPageResult>> getMemberOrders(@PathVariable Long memberId, Pageable pageRequest) {
        return ApiResult.ok(orderService.getOrdersByMember(memberId, pageRequest));
    }

}
