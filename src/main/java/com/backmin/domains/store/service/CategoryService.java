package com.backmin.domains.store.service;

import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.domain.CategoryRepository;
import com.backmin.domains.store.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> readAllCategories() {
        final List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> CategoryDto.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build()).collect(Collectors.toList());
    }

}
