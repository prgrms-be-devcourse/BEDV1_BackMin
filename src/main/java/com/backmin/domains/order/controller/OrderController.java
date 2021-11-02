package com.backmin.domains.order.controller;

import com.backmin.domains.common.dto.ApiResult;
import com.backmin.domains.common.dto.PageResult;
import com.backmin.domains.common.enums.ErrorInfo;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.member.dto.response.MemberOrderPageResult;
import com.backmin.domains.member.service.MemberService;
import com.backmin.domains.order.dto.request.OrderCreateRequest;
import com.backmin.domains.order.dto.request.UpdateOrderStatusRequest;
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
/** todo: url 앞에 구분자 추가할 것*/
@RequestMapping(path = "api/v1/bm/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;

    /** 코드 합쳐지면 의존 수정*/
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;


    @PostMapping
    public ApiResult createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        Member findMember = memberRepository.findById(orderCreateRequest.getMemberId()).get();
        Store store = storeRepository.findById(orderCreateRequest.getStoreId()).get();

        orderService.saveOrder(orderCreateRequest, findMember, store);
        return ApiResult.builder().success(true).build();
    }

    @PostMapping("/{orderId}")
    public ApiResult updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest request) {
        boolean isAuthentication = memberService.authenticateMember(request.getMemberId(), request.getEmail(), request.getPassword());
        /**
         * 추후에 security가 생기면 if문은 aop나 다른 방법으로 사용되어 변경 될 코드
         * 51 ~ 53번 라인이 남아있을 코드
         */
        if (isAuthentication) {
            Member member = memberRepository.findById(request.getMemberId()).get();
            orderService.editOrderStatus(orderId, member, request.getOrderStatus());
            return ApiResult.ok();
        }
        return ApiResult.error(ErrorInfo.NOT_FOUND.getCode(), ErrorInfo.NOT_FOUND.getMessage());
    }

    @GetMapping("/members/{memberId}")
    public ApiResult<PageResult<MemberOrderPageResult>> getMemberOrders(@PathVariable Long memberId, Pageable pageRequest) {
        Member member = memberRepository.findById(memberId).get();
        return ApiResult.ok(orderService.getOrdersByMember(member.getId(), pageRequest));
    }

}
