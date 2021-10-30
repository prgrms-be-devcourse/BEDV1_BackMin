package com.backmin.domains.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryInfoAtList {

    private Long id;

    private String name;

    public static CategoryInfoAtList of(Long id, String name) {
        CategoryInfoAtList categoryInfoAtList = new CategoryInfoAtList();
        categoryInfoAtList.setId(id);
        categoryInfoAtList.setName(name);

        return categoryInfoAtList;
    }
}
