package com.backmin.domains.member.converter;

import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.dto.response.EmailCheckResult;
import com.backmin.domains.member.dto.request.MemberCreateParam;
import com.backmin.domains.member.dto.request.MemberUpdateParam;
import com.backmin.domains.member.dto.response.NicknameCheckResult;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public MemberCreateParam convertMemberToSaveDto(Member member) {
        MemberCreateParam memberCreateParam = new MemberCreateParam();
        memberCreateParam.setEmail(member.getEmail());
        memberCreateParam.setPassword(member.getPassword());
        memberCreateParam.setNickName(member.getNickName());
        memberCreateParam.setAddress(member.getAddress());
        memberCreateParam.setPhoneNumber(member.getPhoneNumber());
        return memberCreateParam;
    }

    public Member convertUpdateDtoToMember(Member member, MemberUpdateParam memberUpdateParam) {
        member.updateInfo(memberUpdateParam.getNickName(),
                memberUpdateParam.getPhoneNumber(),
                memberUpdateParam.getAddress());
        return member;
    }

    public EmailCheckResult convertIsExistedEmailToEmailCheckResult(boolean existsByEmail) {
        EmailCheckResult emailCheckResult = new EmailCheckResult();
        emailCheckResult.setDuplication(existsByEmail);
        return emailCheckResult;
    }


    public NicknameCheckResult convertIsExistedNicknameToNicknameCheckResult(boolean existsByNickName) {
        NicknameCheckResult nicknameCheckResult = new NicknameCheckResult();
        nicknameCheckResult.setDuplication(existsByNickName);
        return nicknameCheckResult;
    }
}


