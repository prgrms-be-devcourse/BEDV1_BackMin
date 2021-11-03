package com.backmin.domains.member.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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
        System.out.println("Member Id : "+ member.getId() + "Member Email : "+ member.getEmail() + "Member Creadted At : " + member.getCreatedAt());
    }

    @Test
    @DisplayName("멤버를 불러온다")
    public void read_member() {
        Optional<Member> member = memberRepository.findById(1L);
        if(!member.isEmpty()) {
            System.out.println("Member Nickname : " + member.get().getNickName());
        }
    }

    @Test
    @DisplayName("멤버를 수정한다")
    public void update_member() {
        Optional<Member> member = memberRepository.findById(1L);

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
