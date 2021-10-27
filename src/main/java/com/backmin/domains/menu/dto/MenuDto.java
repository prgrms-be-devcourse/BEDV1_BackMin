package com.backmin.domains.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDto {

    private Long id;

    private String name;

    private boolean isBest;

    private boolean isSoldOut;

    private boolean isPopular;

    private int price;

}
