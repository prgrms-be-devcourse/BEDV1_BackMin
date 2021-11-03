package com.backmin.domains.member.domain;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void create_member() {
        Member member = Member.builder()
                .email("membertest@gmail.com")
                .password("test01")
                .address("서울시 강남구 역삼동")
                .nickName("이구역개발왕")
                .phoneNumber("010-1234-5678")
                .build();

        Member savedMember = memberRepository.save(member);
        assertThat(savedMember.getNickName(), is("이구역개발왕"));
    }

    @Test
    @DisplayName("멤버를 불러온다")
    public void read_member() {
        Member member = memberRepository.findAll().get(0);
        assertThat(member.getPassword(), is("test01"));
    }

    @Test
    @DisplayName("멤버를 수정한다")
    public void update_member() {
        Member member = memberRepository.findAll().get(0);
        member.updateInfo("삼구역개발왕","010-0000-0000", "부산광역시");
        Member updatedMember = memberRepository.save(member);
        assertThat(updatedMember, is(equalTo(member)));
    }

    @Test
    @DisplayName("멤버를 삭제한다")
    public void delete_member() {
        memberRepository.deleteAll();
        assertThat(memberRepository.findAll().size(), is(0));
    }

}
