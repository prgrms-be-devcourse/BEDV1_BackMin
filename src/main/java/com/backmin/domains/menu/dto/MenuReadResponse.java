package com.backmin.domains.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuReadResponse {

    private Long menuId;

    private String name;

    private boolean isSoldOut;

    private boolean isPopular;

    private int price;

    private String description;

}
