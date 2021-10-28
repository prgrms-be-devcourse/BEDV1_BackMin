package com.backmin.domains.member.converter;

import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.dto.MemberCreateRequest;
import com.backmin.domains.member.dto.MemberUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public Member convertSaveDtoToMember(MemberCreateRequest memberSaveDto) {
        return Member.builder()
                .id(memberSaveDto.getId())
                .email(memberSaveDto.getEmail())
                .password(memberSaveDto.getPassword())
                .nickName(memberSaveDto.getNickName())
                .address(memberSaveDto.getAddress())
                .phoneNumber(memberSaveDto.getPhoneNumber())
                .build();
    }

    public MemberCreateRequest convertMemberToSaveDto(Member member) {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest();
        memberCreateRequest.setId(member.getId());
        memberCreateRequest.setEmail(member.getEmail());
        memberCreateRequest.setPassword(member.getPassword());
        memberCreateRequest.setNickName(member.getNickName());
        memberCreateRequest.setAddress(member.getAddress());
        memberCreateRequest.setPhoneNumber(member.getPhoneNumber());
        return memberCreateRequest;
    }

    public Member convertUpdateDtoToMember(MemberUpdateRequest memberUpdateRequest) {
        return Member.builder()
                .id(memberUpdateRequest.getId())
                .email(memberUpdateRequest.getEmail())
                .password(memberUpdateRequest.getPassword())
                .nickName(memberUpdateRequest.getNickName())
                .address(memberUpdateRequest.getAddress())
                .phoneNumber(memberUpdateRequest.getPhoneNumber())
                .build();
    }

}


