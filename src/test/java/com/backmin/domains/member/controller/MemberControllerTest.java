package com.backmin.domains.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backmin.domains.member.converter.MemberConverter;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.member.dto.MemberCreateRequest;
import com.backmin.domains.member.dto.MemberUpdateRequest;
import com.backmin.domains.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberController memberController;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberConverter memberConverter;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    void setUp() {
        MemberCreateRequest memberCreateRequest = MemberCreateRequest.of(
                1L,
                "test01@gmail.com",
                "1234",
                "010-1234-5678",
                "이구역개발킹",
                "서울시 강남구");

        memberService.save(memberCreateRequest);
    }

    @Test
    @DisplayName("중복된 이메일로 가입하려 할 경우")
    void memberCallTest() throws Exception {
        MemberCreateRequest memberCreateRequest = MemberCreateRequest.of(
                2L,
                "test01@gmail.com",
                "1234",
                "010-1234-5678",
                "이구역개발퀸",
                "서울시 강남구");

        mockMvc.perform(post("/api/v1/bm/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberCreateRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("data").doesNotExist())
                .andExpect(jsonPath("serverDatetime").isString());
    }

    @Test
    @DisplayName("중복 이메일 존재할 경우")
    void duplicationEmailCheck_true() throws Exception {
        memberRepository.save(Member.builder()
                .id(3L)
                .email("test@gmail.com")
                .password("1234")
                .phoneNumber("010-1234-5678")
                .nickName("짱짱맨")
                .address("서울시 강남구")
                .build());
        mockMvc.perform(get("/api/v1/bm/members/email/{email}", "test@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @DisplayName("중복 이메일 존재하지 않을 경우")
    void duplicationEmailCheck_false() throws Exception {
        memberRepository.save(Member.builder()
                .id(3L)
                .email("test03@gmail.com")
                .password("1234")
                .phoneNumber("010-1234-5678")
                .nickName("짱짱맨")
                .address("서울시 강남구")
                .build());
        mockMvc.perform(get("/api/v1/bm/members/email/{email}", "test@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @DisplayName("멤버 주소 수정")
    void updateMember() {
        Optional<Member> foundedMember = memberRepository.findById(1L);
        memberController.updateMember(foundedMember.get().getId(),
                MemberUpdateRequest.of(foundedMember.get().getId(), foundedMember.get().getPhoneNumber(), foundedMember.get().getNickName(),
                        "서울시 송파구"));
        Optional<Member> foundedNowMember = memberRepository.findById(1L);

        System.out.println(foundedNowMember.get().getId());
        System.out.println(foundedNowMember.get().getEmail());
        System.out.println(foundedNowMember.get().getPassword());
        System.out.println(foundedNowMember.get().getNickName());
        System.out.println(foundedNowMember.get().getAddress());
        System.out.println(foundedNowMember.get().getPhoneNumber());
        System.out.println(foundedNowMember.get().getCreatedAt());
        System.out.println(foundedNowMember.get().getUpdatedAt());
    }

    @Test
    @DisplayName("닉네임 중복이 존재할 경우")
    void duplicationNicknameCheck_true() throws Exception {
        mockMvc.perform(get("/api/v1/bm/members/nickname/{nickname}", "이구역개발킹")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @DisplayName("닉네임 중복이 존재하지 않을 경우")
    void duplicationNicknameCheck_false() throws Exception {
        mockMvc.perform(get("/api/v1/bm/members/nickname/{nickname}", "이구역개발퀸")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}