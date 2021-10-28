package com.backmin.domains.member.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.backmin.domains.member.converter.MemberConverter;
import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.member.dto.MemberCreateRequest;
import com.backmin.domains.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MemberControllerTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberConverter memberConverter;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("멤버 생성")
    public void create_member() {
        MemberCreateRequest createdMember1 = new MemberCreateRequest();
        createdMember1.setId(1L);
        createdMember1.setEmail("test@gmail.com");
        createdMember1.setPassword("1234");
        createdMember1.setNickName("이구역개발왕");
        createdMember1.setAddress("서울시 강남구");
        createdMember1.setPhoneNumber("010-1234-5678");

        MemberCreateRequest createdMember2 = new MemberCreateRequest();
        createdMember2.setId(2L);
        createdMember2.setEmail("test1@gmail.com");
        createdMember2.setPassword("1234");
        createdMember2.setNickName("이구역개발왕");
        createdMember2.setAddress("서울시 강서구");
        createdMember2.setPhoneNumber("010-1234-5678");

        Long id1 = memberService.save(createdMember1);
        Long id2 = memberService.save(createdMember2);
    }

}