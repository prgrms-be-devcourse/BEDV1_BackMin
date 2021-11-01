package com.backmin.domains.store.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryAtListResult {

    private Long id;

    private String name;

    public static CategoryAtListResult of(Long id, String name) {
        CategoryAtListResult categoryAtListResult = new CategoryAtListResult();
        categoryAtListResult.setId(id);
        categoryAtListResult.setName(name);

        return categoryAtListResult;
    }
}
