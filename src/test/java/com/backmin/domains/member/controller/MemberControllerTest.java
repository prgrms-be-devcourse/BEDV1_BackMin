package com.backmin.domains.member.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.backmin.domains.BaseControllerTest;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.dto.request.MemberCreateParam;
import com.backmin.domains.member.dto.request.MemberUpdateParam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

class MemberControllerTest extends BaseControllerTest {

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    @BeforeAll
    void setUp() {
        Member member = Member.of("testemail01@gmail.com",
                "testpassword",
                "010-1112-2222",
                "야이야이야",
                "인천광역시"
        );

        memberRepository.save(member);
    }

    @Test
    void create_member() throws Exception {
        MemberCreateParam memberCreateParam = new MemberCreateParam();
        memberCreateParam.setEmail("test@gmail.com");
        memberCreateParam.setPassword("test1234");
        memberCreateParam.setNickName("이구역개발퀸");
        memberCreateParam.setAddress("부산광역시");
        memberCreateParam.setPhoneNumber("010-1122-3344");

        mockMvc.perform(post("/api/v1/bm/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberCreateParam)))
                .andDo(print())
                .andDo(document("member-save",
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("phoneNumber"),
                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("nickName"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("address")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )));
    }

    @Test
    void update_member() throws Exception{
        Member foundMember = memberRepository.findAll().get(0);

        System.out.println(foundMember.getId());

        MemberUpdateParam memberUpdateParam = new MemberUpdateParam();
        memberUpdateParam.setAddress("광주광역시");
        memberUpdateParam.setPassword("testpassword");
        memberUpdateParam.setEmail("testemail01@gmail.com");

        mockMvc.perform(put("/api/v1/bm/members/{id}", foundMember.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberUpdateParam)))
                .andDo(print())
                .andDo(document("member-save",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NULL).description("id"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.NULL).description("phoneNumber"),
                                fieldWithPath("nickName").type(JsonFieldType.NULL).description("nickName"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("address")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )));

        Member afterMember = memberRepository.findAll().get(0);
        assertThat(afterMember.getAddress(), is("광주광역시"));
    }
}