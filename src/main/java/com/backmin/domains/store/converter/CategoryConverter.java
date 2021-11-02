package com.backmin.domains.store.converter;

import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.dto.response.CategoryAtListResult;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public CategoryAtListResult convertCategoryToCategoryInfoAtList(Category category) {
        CategoryAtListResult categoryAtListResult = new CategoryAtListResult();
        categoryAtListResult.setId(category.getId());
        categoryAtListResult.setName(category.getName());

        return categoryAtListResult;
    }

}
