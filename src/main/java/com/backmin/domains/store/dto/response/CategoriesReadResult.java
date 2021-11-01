package com.backmin.domains.store.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriesReadResult {

    private List<CategoryAtListResult> categories;

    public static CategoriesReadResult of(List<CategoryAtListResult> categories) {
        CategoriesReadResult categoriesReadResult = new CategoriesReadResult();
        categoriesReadResult.setCategories(categories);

        return categoriesReadResult;
    }
}
