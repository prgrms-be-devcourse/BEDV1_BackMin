package com.backmin.domains.order.service;

import com.backmin.domains.member.domain.Member;
import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.domain.MenuOption;
import com.backmin.domains.menu.dto.MenuReadRequest;
import com.backmin.domains.order.domain.Order;
import com.backmin.domains.order.domain.OrderMenu;
import com.backmin.domains.order.domain.OrderMenuOption;
import com.backmin.domains.order.domain.OrderRepository;
import com.backmin.domains.order.domain.OrderStatus;
import com.backmin.domains.order.dto.OrderCreateRequest;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.domain.StoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void saveOrder(OrderCreateRequest request, Member member, Store store) {
        Order order = Order.of(request.getAddress(), request.getRequirement(), request.getPayment(), member, store.getDeliveryTip());
        addOrderMenu(request, store, order);

        if (order.getTotalPrice() < store.getMinOrderPrice()) {
            throw new RuntimeException("최소 주문 금액을 넘어야합니다.");
        }
        orderRepository.save(order);
    }

    private void addOrderMenu(OrderCreateRequest request, Store store, Order order) {
        List<Menu> menus = store.getMenus();

        for (MenuReadRequest menuDto : request.getMenuReadRequests()) {
            for (Menu menu : menus) {
                if (menu.getId().equals(menuDto.getId())) {
                    OrderMenu orderMenu = OrderMenu.of(menu, menu.getPrice(), menuDto.getQuantity());
                    addOrderMenuOption(menuDto, menu, orderMenu);
                    order.addOrderMenu(orderMenu);
                }
            }
        }
    }

    private void addOrderMenuOption(MenuReadRequest menuDto, Menu menu, OrderMenu orderMenu) {
        for (Long menuOptionId : menuDto.getMenuOptionId()) {
            for (MenuOption menuOption : menu.getMenuOptions()) {
                if (menuOption.getId().equals(menuOptionId)) {
                    OrderMenuOption orderMenuOption = OrderMenuOption.of(menuOption, menuOption.getPrice());
                    orderMenu.addOrderMenuOption(orderMenuOption);
                }
            }
        }
    }

    public void editOrderStatus(Long id, OrderStatus orderStatus) {

    }
}
