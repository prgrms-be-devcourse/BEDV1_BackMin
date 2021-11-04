package com.backmin.domains.store.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.domain.CategoryRepository;
import com.backmin.domains.store.dto.response.CategoriesReadResult;
import com.backmin.domains.store.dto.response.CategoryAtListResult;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DisplayName("CategoryService 테스트")
class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("카테고리 목록 조회 테스트")
    void test_readAllCategories() {
        // given
        Category category1 = Category.of("한식");
        Category category2 = Category.of("중식");
        Category category3 = Category.of("양식");
        List<Category> categories = List.of(category1, category2, category3);
        categoryRepository.saveAll(categories);

        // when
        CategoriesReadResult categoriesReadResult = categoryService.readAllCategories();

        // then
        assertThat(categoriesReadResult, notNullValue());
        assertThat(categoriesReadResult.getCategories().size(), is(categories.size()));
    }

}
