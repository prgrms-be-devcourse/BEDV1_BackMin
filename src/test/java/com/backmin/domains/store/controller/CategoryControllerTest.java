package com.backmin.domains.store.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.domain.CategoryRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@DisplayName("CategoryController 테스트")
class CategoryControllerTest {

    private final String BASE_URI = "/api/v1/bm/categories";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 목록 조회 테스트")
    void test_list() throws Exception {
        // given
        Category category1 = Category.of("한식");
        Category category2 = Category.of("중식");
        Category category3 = Category.of("양식");
        List<Category> categories = List.of(category1, category2, category3);
        categoryRepository.saveAll(categories);

        // when  // then
        mockMvc.perform(get(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").exists())
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data.categories").exists())
                .andExpect(jsonPath("serverDatetime").exists())
                .andDo(print())
                .andDo(document("category-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터").optional(),
                                fieldWithPath("data.categories").type(JsonFieldType.ARRAY).description("카테고리 목록"),
                                fieldWithPath("data.categories[].id").type(JsonFieldType.NUMBER).description("카테고리 Id"),
                                fieldWithPath("data.categories[].name").type(JsonFieldType.STRING).description("카테고리명")
                        )));
    }

}
