package com.backmin.domains.store.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadAllCategoriesResponse {

    private List<CategoryInfoAtList> categories;

    public static ReadAllCategoriesResponse of(List<CategoryInfoAtList> categorys) {
        ReadAllCategoriesResponse readAllCategoriesResponse = new ReadAllCategoriesResponse();
        readAllCategoriesResponse.setCategories(categorys);

        return readAllCategoriesResponse;
    }
}
