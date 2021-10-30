package com.backmin.domains.store.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backmin.domains.store.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("StoreController 테스트")
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StoreService storeService;

    @Test
    @DisplayName("카테고리 별 가게 목록 조회 테스트")
    void test_list() throws Exception {
        mockMvc.perform(get("/api/v1/bm/categories/{categoryId}/stores", 1L)
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data.totalCount").exists())
                .andExpect(jsonPath("data.pageNumber").exists())
                .andExpect(jsonPath("data.pageSize").exists())
                .andExpect(jsonPath("data.hasNext").exists())
                .andExpect(jsonPath("data.list").exists())
                .andExpect(jsonPath("serverDatetime").exists())
                .andDo(print());
    }

}
