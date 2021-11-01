package com.backmin.domains.member.converter;

import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.dto.request.MemberCreateParam;
import com.backmin.domains.member.dto.request.MemberUpdateParam;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public Member convertSaveDtoToMember(MemberCreateParam memberSaveDto) {
        return Member.of(memberSaveDto.getId(),
                memberSaveDto.getEmail(),
                memberSaveDto.getPassword(),
                memberSaveDto.getPhoneNumber(),
                memberSaveDto.getNickName(),
                memberSaveDto.getAddress());
    }

    public MemberCreateParam convertMemberToSaveDto(Member member) {
        return MemberCreateParam.of(member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.getNickName(),
                member.getAddress(),
                member.getPhoneNumber());
    }

    public Member convertUpdateDtoToMember(Member member, MemberUpdateParam memberUpdateParam) {
        member.updateInfo(memberUpdateParam.getNickName(),
                memberUpdateParam.getPhoneNumber(),
                memberUpdateParam.getAddress());
        return member;
    }

}


