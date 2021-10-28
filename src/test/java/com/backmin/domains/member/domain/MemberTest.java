package com.backmin.domains.member.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Optional;

@Slf4j
@DataJpaTest
class MemberTest {

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

        memberRepository.save(member);
        //log.info("생성된 Member Info \nMember Id : "+ member.getId() + "Member Email : "+ member.getEmail() + "Member Creadted At : " + member.getCreatedAt());
    }

    @Test
    @DisplayName("멤버를 불러온다")
    public void read_member() {
        Optional<Member> member = memberRepository.findById(1L);
        if(!member.isEmpty()) {
            assertThat(member.get().getNickName(), is(equalTo("이구역개발왕")));
        }
    }

    @Test
    @DisplayName("멤버를 수정한다")
    public void update_member() {
        Optional<Member> member = memberRepository.findById(1L);
        String beforeNickname = member.get().getNickName();
        member.get().changeNickName("개발왕은나야");

        assertThat(member.get().getNickName(), is(equalTo(beforeNickname)));
    }

    @Test
    @DisplayName("멤버를 삭제한다")
    public void delete_member() {
        memberRepository.deleteAll();
        //List<Member> members = memberRepository.findAll();

        assertThat(memberRepository.count(), is(equalTo(0)));
    }

}