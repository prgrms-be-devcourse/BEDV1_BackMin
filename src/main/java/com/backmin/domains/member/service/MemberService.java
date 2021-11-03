package com.backmin.domains.member.service;

import com.backmin.config.exception.BusinessException;
import com.backmin.domains.common.enums.ErrorInfo;
import com.backmin.domains.member.converter.MemberConverter;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.member.dto.request.EmailCheckParam;
import com.backmin.domains.member.dto.request.MemberCreateParam;
import com.backmin.domains.member.dto.request.MemberUpdateParam;
import com.backmin.domains.member.dto.request.NicknameCheckParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberConverter memberConverter;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberCreateParam memberCreateParam) {
        if (checkMemberEmail(memberCreateParam.getEmail()).isDuplication()) {
            throw new BusinessException(ErrorInfo.DUPLICATE_EMAIL);
        }
        if (checkMemberNickname(memberCreateParam.getNickName()).isDuplication()) {
            throw new BusinessException(ErrorInfo.DUPLICATE_NICKNAME);
        }
        memberRepository.save(Member.of(memberCreateParam.getEmail(), memberCreateParam.getPassword(),
                memberCreateParam.getPhoneNumber(), memberCreateParam.getNickName(), memberCreateParam.getAddress()));
    }

    @Transactional
    public void update(Long memberId, MemberUpdateParam memberUpdateParam) {
        memberRepository.findById(memberId)
                .map(member -> memberConverter.convertUpdateDtoToMember(member, memberUpdateParam))
                .orElseThrow(() -> new BusinessException(ErrorInfo.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public MemberCreateParam findOne(Long id) {
        return memberRepository.findById(id)
                .map(memberConverter::convertMemberToSaveDto)
                .orElseThrow(() -> new BusinessException(ErrorInfo.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<MemberCreateParam> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable).map(memberConverter::convertMemberToSaveDto);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member foundMember = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ErrorInfo.MEMBER_NOT_FOUND));
        memberRepository.delete(foundMember);
    }

    public EmailCheckParam checkMemberEmail(String email) {
        EmailCheckParam emailCheckParam = new EmailCheckParam();
        emailCheckParam.setDuplication(memberRepository.existsByEmail(email));
        return emailCheckParam;
    }

    public NicknameCheckParam checkMemberNickname(String nickname) {
        NicknameCheckParam nicknameCheckParam = new NicknameCheckParam();
        nicknameCheckParam.setDuplication(memberRepository.existsByNickName(nickname));
        return nicknameCheckParam;
    }

    public boolean authenticateMember(Long memberId, String email, String password) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ErrorInfo.MEMBER_NOT_FOUND));
        return member.getEmail().equals(email) && member.getPassword().equals(password);
    }

}
