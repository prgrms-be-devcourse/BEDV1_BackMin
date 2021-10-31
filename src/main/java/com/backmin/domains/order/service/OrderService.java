package com.backmin.domains.order.service;

import com.backmin.domains.member.domain.Member;
import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.domain.MenuOption;
import com.backmin.domains.menu.domain.MenuOptionRepository;
import com.backmin.domains.menu.domain.MenuRepository;
import com.backmin.domains.menu.dto.MenuDto;
import com.backmin.domains.menu.dto.MenuOptionDto;
import com.backmin.domains.order.domain.Order;
import com.backmin.domains.order.domain.OrderMenu;
import com.backmin.domains.order.domain.OrderMenuOption;
import com.backmin.domains.order.domain.OrderRepository;
import com.backmin.domains.order.dto.OrderCreateRequest;
import com.backmin.domains.store.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * 임시 의존
     */
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;

    @Transactional
    public void saveOrder(OrderCreateRequest request, Member member, Store store) {
        Order order = Order.of(request.getAddress(), request.getRequirement(), request.getPayment(), member, store.getDeliveryTip());
        addOrderMenu(request, store, order);
        orderRepository.save(order);
    }

    private void addOrderMenu(OrderCreateRequest request, Store store, Order order) {
        for (MenuDto menuDto : request.getMenuDtos()) {
            Menu menu = menuRepository.findById(menuDto.getId()).get();
            OrderMenu orderMenu = OrderMenu.of(menu, menuDto.getQuantity());
            addOrderMenuOption(menuDto, menu, orderMenu);
            order.addOrderMenu(orderMenu);
        }

        /**
         * store를 조회해올때 메뉴도 패치조인 필요
         */
//        for (MenuDto menuDto : request.getMenuDtos()) {
//            for (Menu menu : store.getMenus()) {
//                if (menu.equals(menuDto.getId())) {
//                    OrderMenu orderMenu = OrderMenu.of(menu, menuDto.getQuantity());
//                    addOrderMenuOption(menuDto, menu, orderMenu);
//                    order.addOrderMenu(orderMenu);
//                }
//            }
//        }

    }

    private void addOrderMenuOption(MenuDto menuDto, Menu menu, OrderMenu orderMenu) {
        for (MenuOptionDto menuOptionDto : menuDto.getMenuOptionDtos()) {
            MenuOption menuOption = menuOptionRepository.findById(menuOptionDto.getId()).get();
            OrderMenuOption orderMenuOption = OrderMenuOption.of(menuOption, menuOptionDto.getQuantity());
            orderMenu.addOrderMenuOption(orderMenuOption);
        }

        /**
         * menuOption lazy로딩이 안됨
         */
//        for (MenuOptionDto menuOptionDto : menuDto.getMenuOptionDtos()) {
//            for (MenuOption menuOption : menu.getMenuOptions()) {
//                if (menuOption.equals(menuOptionDto.getId())) {
//                    OrderMenuOption orderMenuOption = OrderMenuOption.of(menuOption, menuOptionDto.getQuantity());
//                    orderMenu.addOrderMenuOption(orderMenuOption);
//                }
//            }
//        }
    }

}
