package com.backmin.domains.store.converter;

import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.dto.CategoryInfoAtList;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public CategoryInfoAtList convertCategoryToCategoryInfoAtList(Category category) {
        return CategoryInfoAtList.of(category.getId(), category.getName());
    }

}
