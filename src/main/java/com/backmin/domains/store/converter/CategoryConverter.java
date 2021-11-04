package com.backmin.domains.store.converter;

import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.dto.response.CategoriesReadResult;
import com.backmin.domains.store.dto.response.CategoryAtListResult;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public CategoriesReadResult convertCategoryToCategoryInfoAtList(List<Category> categories) {
        CategoriesReadResult categoriesReadResult = new CategoriesReadResult();
        categoriesReadResult.setCategories(categories.stream().map(category -> {
                    CategoryAtListResult categoryAtListResult = new CategoryAtListResult();
                    categoryAtListResult.setId(category.getId());
                    categoryAtListResult.setName(category.getName());
                    return categoryAtListResult;
                })
                .collect(Collectors.toList()));

        return categoriesReadResult;
    }

}
