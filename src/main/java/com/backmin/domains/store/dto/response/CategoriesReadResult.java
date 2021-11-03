package com.backmin.domains.store.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriesReadResult {

    private List<CategoryAtListResult> categories;

}
