package com.backmin.domains.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReadAllCategoriesResponse {

    private List<CategoryDto> categoryDtos;

}
