package com.backmin.domains.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuOptionDto {

    private Long id;

    private String name;

    private Long topOptionId;

    private int maxOptionQuantity;

    private int minOptionQuantity;

    private boolean isSoldOut;

}
