package com.backmin.domains.order.converter;

import com.backmin.domains.common.dto.PageResult;
import com.backmin.domains.member.dto.response.MemberOrderPageResult;
import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.dto.response.MenuReadResult;
import com.backmin.domains.order.domain.Order;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {

    public PageResult<MemberOrderPageResult> convertOrderToMemberOrderPageResponse(Page<Order> orders) {
        List<MemberOrderPageResult> memberOrderPageResponse = orders.getContent().stream()
                .map(this::createMemberOrderPageResult)
                .collect(Collectors.toList());
        return createPageResult(orders, memberOrderPageResponse);
    }

    private PageResult<MemberOrderPageResult> createPageResult(Page<Order> orders, List<MemberOrderPageResult> memberOrderPageResponse) {
        PageResult<MemberOrderPageResult> pageResult = new PageResult<>();
        pageResult.setPageNumber(orders.getNumber());
        pageResult.setPageSize(orders.getSize());
        pageResult.setHasNext(orders.hasNext());
        pageResult.setTotalCount(orders.getNumberOfElements());
        pageResult.setList(memberOrderPageResponse);
        return pageResult;
    }

    private MemberOrderPageResult createMemberOrderPageResult(Order order) {
        MemberOrderPageResult response = new MemberOrderPageResult();
        response.setOrderId(order.getId());
        response.setStoreId(order.getStore().getId());
        response.setOrderDateTime(order.getRequestAt());
        response.setStoreName(order.getStore().getName());
        response.setMenuReadResponses(
                order.getOrderMenus().stream()
                        .map(this::createMenuReadResult)
                        .collect(Collectors.toList())
        );
        return response;
    }

    private MenuReadResult createMenuReadResult(com.backmin.domains.order.domain.OrderMenu orderMenu) {
        MenuReadResult menuReadResult = new MenuReadResult();
        Menu menu = orderMenu.getMenu();
        menuReadResult.setMenuId(menu.getId());
        menuReadResult.setName(menu.getName());
        menuReadResult.setPopular(menu.isPopular());
        menuReadResult.setSoldOut(menu.isSoldOut());
        menuReadResult.setDescription(menu.getDescription());
        return menuReadResult;
    }

}
