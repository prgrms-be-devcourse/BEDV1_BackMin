package com.backmin.domains.menu.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuAtStoreListResult {

    private Long id;

    private String name;

    private String imageUrl;

    private boolean isBest;

    private boolean isSoldOut;

    private boolean isPopular;

    private int price;

    private List<MenuOptionAtStoreListResult> menuOptions;

}
