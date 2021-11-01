package com.backmin.domains.member.service;

import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean authenticateMember(Long memberId, String email, String password) {
        Member member = memberRepository.findById(memberId).get();
        return member.getEmail().equals(email) && member.getPassword().equals(password);
    }

}
