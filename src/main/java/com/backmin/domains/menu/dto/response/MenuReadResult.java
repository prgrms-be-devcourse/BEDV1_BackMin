package com.backmin.domains.menu.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuReadResult {

    private Long menuId;

    private String name;

    private boolean isSoldOut;

    private boolean isPopular;

    private int price;

    private String description;

}
