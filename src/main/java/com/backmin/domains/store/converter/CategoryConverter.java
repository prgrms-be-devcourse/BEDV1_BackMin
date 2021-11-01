package com.backmin.domains.store.converter;

import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.dto.response.CategoryAtListResult;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public CategoryAtListResult convertCategoryToCategoryInfoAtList(Category category) {
        return CategoryAtListResult.of(category.getId(), category.getName());
    }

}
