package com.backmin.domains.member.converter;

import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.dto.MemberCreateRequest;
import com.backmin.domains.member.dto.MemberUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public Member convertSaveDtoToMember(MemberCreateRequest memberSaveDto) {
        return new Member(memberSaveDto.getId(),
                memberSaveDto.getEmail(),
                memberSaveDto.getPassword(),
                memberSaveDto.getPhoneNumber(),
                memberSaveDto.getNickName(),
                memberSaveDto.getAddress());
    }

    public MemberCreateRequest convertMemberToSaveDto(Member member) {
        return MemberCreateRequest.of(member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.getNickName(),
                member.getAddress(),
                member.getPhoneNumber());
    }

    public Member convertUpdateDtoToMember(Member member, MemberUpdateRequest memberUpdateRequest) {
        member.updateInfo(memberUpdateRequest.getNickName(),
                memberUpdateRequest.getPhoneNumber(),
                memberUpdateRequest.getAddress());
        return member;
    }

}


