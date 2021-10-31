package com.backmin.domains.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuOptionDto {

    private Long id;

    private String name;

    private int price;

    private int quantity = 1;

}
