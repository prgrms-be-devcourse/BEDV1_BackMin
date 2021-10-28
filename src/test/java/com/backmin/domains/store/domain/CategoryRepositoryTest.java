package com.backmin.domains.store.domain;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
@DisplayName("categoryRepository 테스트")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 목록 조회 테스트")
    void test_findAll() {
        // given
        Category category1 = Category.of("한식");
        Category category2 = Category.of("중식");
        Category category3 = Category.of("양식");
        List<Category> categories = List.of(category1, category2, category3);
        categoryRepository.saveAll(categories);

        // when
        List<Category> foundCategories = categoryRepository.findAll();

        // then
        assertThat(foundCategories, notNullValue());
        assertThat(foundCategories.size(), is(categories.size()));
    }
}
