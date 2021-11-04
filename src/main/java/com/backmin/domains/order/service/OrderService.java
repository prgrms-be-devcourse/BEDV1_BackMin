package com.backmin.domains.order.service;

import com.backmin.config.exception.BusinessException;
import com.backmin.config.util.AssertThrow;
import com.backmin.domains.common.dto.PageResult;
import com.backmin.domains.common.enums.ErrorInfo;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.member.dto.response.MemberOrderPageResult;
import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.domain.MenuOption;
import com.backmin.domains.menu.dto.request.MenuReadParam;
import com.backmin.domains.order.converter.OrderConverter;
import com.backmin.domains.order.domain.Order;
import com.backmin.domains.order.domain.OrderMenu;
import com.backmin.domains.order.domain.OrderMenuOption;
import com.backmin.domains.order.domain.OrderRepository;
import com.backmin.domains.order.domain.OrderStatus;
import com.backmin.domains.order.dto.request.CreateOrderParam;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.domain.StoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final OrderConverter orderConverter;

    @Transactional
    public void saveOrder(CreateOrderParam request) {
        Member member = findMember(request.getMemberId());
        Store store = findStore(request.getStoreId());
        Order order = Order.of(request.getAddress(), request.getRequirement(), request.getPayment(), member, store.getDeliveryTip());
        addOrderMenuToOrder(request, order, store.getMenus());
        AssertThrow.isTrue(order.getTotalPrice() < store.getMinOrderPrice(), ErrorInfo.ORDER_MIN_PRICE);
        orderRepository.save(order);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ErrorInfo.MEMBER_NOT_FOUND));
    }

    private Store findStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(() -> new BusinessException(ErrorInfo.STORE_NOT_FOUND));
    }

    private void addOrderMenuToOrder(CreateOrderParam request, Order order, List<Menu> menus) {
        for (MenuReadParam menuReadParam : request.getMenuReadParams()) {
            Menu menu = searchMenu(menus, menuReadParam.getId());
            OrderMenu orderMenu = OrderMenu.of(menu, menu.getPrice(), menuReadParam.getQuantity());
            addOrderMenuOptionToOrderMenu(menuReadParam, menu, orderMenu);
            order.addOrderMenu(orderMenu);
        }
    }

    private Menu searchMenu(List<Menu> menus, Long menuRequestId) {
        return menus.stream()
                .filter(menu -> menu.getId().equals(menuRequestId))
                .findAny()
                .orElseThrow(() -> new BusinessException(ErrorInfo.MENU_NOT_FOUND));
    }

    private void addOrderMenuOptionToOrderMenu(MenuReadParam menuReadParam, Menu menu, OrderMenu orderMenu) {
        for (Long menuOptionId : menuReadParam.getMenuOptionIds()) {
            MenuOption menuOption = searchMenuOption(menu.getMenuOptions(), menuOptionId);
            OrderMenuOption orderMenuOption = OrderMenuOption.of(menuOption, menuOption.getPrice());
            orderMenu.addOrderMenuOption(orderMenuOption);
        }
    }

    private MenuOption searchMenuOption(List<MenuOption> menuOptions, Long menuOptionId) {
        return menuOptions.stream()
                .filter(menuOption -> menuOption.getId().equals(menuOptionId))
                .findAny()
                .orElseThrow(() -> new BusinessException(ErrorInfo.MENU_OPTION_NOT_FOUND));
    }
    
    @Transactional
    public void editOrderStatus(Long orderId, Long memberId, OrderStatus orderStatus) {
        Member member = findMember(memberId);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BusinessException(ErrorInfo.ORDER_NOT_FOUND));
        isMemberByCustomer(member, orderStatus, order);
        isMemberByStoreOwner(member, orderStatus, order);
    }

    /**
     * 주문 상태변경을 요청하는 회원이 고객일 경우 취소만 가능하다.
     */
    private void isMemberByCustomer(Member member, OrderStatus orderStatus, Order order) {
        if (order.getMember().equals(member)) {
            if (orderStatus == OrderStatus.CANCELED) {
                order.changeOrderStatus(orderStatus);
            }
            AssertThrow.isTrue(orderStatus == OrderStatus.DELIVERED, ErrorInfo.ORDER_STATUS_AUTHORITY);
        }
    }

    /**
     * 주문 상태변경을 요청하는 회원이 사장일 경우 수락과 취소가 가능하다.
     */
    private void isMemberByStoreOwner(Member member, OrderStatus orderStatus, Order order) {
        if (order.getStore().getMember().equals(member)) {
            order.changeOrderStatus(orderStatus);
        }
    }

    public PageResult<MemberOrderPageResult> getOrdersByMember(Long memberId, Pageable pageRequest) {
        Page<Order> orders = orderRepository.findAllByMemberId(memberId, pageRequest);
        return orderConverter.convertOrderToMemberOrderPageResponse(orders);
    }

}
