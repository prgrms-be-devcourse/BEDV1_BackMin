package com.backmin.domains.order.dto;

import com.backmin.domains.menu.domain.MenuOption;
import com.backmin.domains.order.domain.OrderMenu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMenuOptionDto {

    private Long id;

    private MenuOption menuOption;

    private OrderMenu orderMenu;
}
