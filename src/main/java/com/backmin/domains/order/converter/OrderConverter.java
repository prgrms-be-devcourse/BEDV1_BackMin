package com.backmin.domains.order.converter;

import com.backmin.domains.common.dto.PageDto;
import com.backmin.domains.member.dto.MemberOrderPageResponse;
import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.dto.MenuReadResponse;
import com.backmin.domains.order.domain.Order;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {

    public PageDto<MemberOrderPageResponse> convertOrderToMemberOrderPageResponse(Page<Order> orders) {
        List<MemberOrderPageResponse> memberOrderPageResponses = orders.getContent().stream()
                .map(order -> {
                    MemberOrderPageResponse response = new MemberOrderPageResponse();
                    response.setOrderId(order.getId());
                    response.setStoreId(order.getStore().getId());
                    response.setOrderDateTime(order.getRequestAt());
                    response.setStoreName(order.getStore().getName());
                    response.setMenuReadResponses(
                            order.getOrderMenus().stream()
                                    .map(orderMenu -> {
                                        MenuReadResponse menuReadResponse = new MenuReadResponse();
                                        Menu menu = orderMenu.getMenu();
                                        menuReadResponse.setMenuId(menu.getId());
                                        menuReadResponse.setName(menu.getName());
                                        menuReadResponse.setPopular(menu.isPopular());
                                        menuReadResponse.setSoldOut(menu.isSoldOut());
                                        menuReadResponse.setDescription(menu.getDescription());
                                        return menuReadResponse;
                                    }).toList()
                    );
                    return response;
                }).toList();

        PageDto<MemberOrderPageResponse> pageDto = new PageDto<>();
        pageDto.setPageNumber(orders.getNumber());
        pageDto.setPageSize(orders.getSize());
        pageDto.setHasNext(orders.hasNext());
        pageDto.setTotalCount(orders.getNumberOfElements());
        pageDto.setList(memberOrderPageResponses);
        return pageDto;
    }

}
