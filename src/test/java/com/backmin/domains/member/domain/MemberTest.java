package com.backmin.domains.member.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void create_member() {
        Member member = new Member(1L,
                "membertest@gmail.com",
                "test01",
                "이구역개발왕",
                "010-1234-5678",
                "서울시 강남구 역삼동");

        Member member1 = new Member(1L,
                "membertest@gmail.com",
                "test01",
                "이구역개발왕",
                "010-1234-5678",
                "서울시 강남구 역삼동");

        memberRepository.save(member);
        memberRepository.save(member1);

        System.out.println("Member Id : " + member.getId() + "Member Email : " + member.getEmail() + "Member Creadted At : " + member.getCreatedAt());
    }

    @Test
    @DisplayName("멤버를 불러온다")
    public void read_member() {
        Optional<Member> member = memberRepository.findById(1L);
        if (!member.isEmpty()) {
            System.out.println("Member Nickname : " + member.get().getNickName());
        }
    }

    @Test
    @DisplayName("멤버를 수정한다")
    public void update_member() {
        Optional<Member> member = memberRepository.findById(1L);
        member.get().changeNickName("개발왕은나야");

        System.out.println("Changed Member Nickname : " + member.get().getNickName());
    }

    @Test
    @DisplayName("멤버를 삭제한다")
    public void delete_member() {
        memberRepository.deleteAll();
        //List<Member> members = memberRepository.findAll();

        System.out.println(memberRepository.count());
    }

}