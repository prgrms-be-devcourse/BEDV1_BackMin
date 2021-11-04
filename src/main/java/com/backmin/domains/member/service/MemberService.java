package com.backmin.domains.member.service;

import com.backmin.config.exception.BusinessException;
import com.backmin.config.util.AssertThrow;
import com.backmin.domains.common.enums.ErrorInfo;
import com.backmin.domains.member.converter.MemberConverter;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.member.dto.response.EmailCheckResult;
import com.backmin.domains.member.dto.request.MemberCreateParam;
import com.backmin.domains.member.dto.request.MemberUpdateParam;
import com.backmin.domains.member.dto.response.NicknameCheckResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberConverter memberConverter;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberCreateParam memberCreateParam) {
        AssertThrow.isTrue(memberRepository.existsByEmail(memberCreateParam.getEmail()), ErrorInfo.DUPLICATE_EMAIL);
        AssertThrow.isTrue(memberRepository.existsByNickName(memberCreateParam.getNickName()), ErrorInfo.DUPLICATE_NICKNAME);
        memberRepository.save(Member.of(memberCreateParam.getEmail(), memberCreateParam.getPassword(),
                memberCreateParam.getPhoneNumber(), memberCreateParam.getNickName(), memberCreateParam.getAddress()));
    }

    @Transactional
    public void update(Long memberId, MemberUpdateParam memberUpdateParam) {
        memberRepository.findById(memberId)
                .map(member -> memberConverter.convertUpdateDtoToMember(member, memberUpdateParam))
                .orElseThrow(() -> new BusinessException(ErrorInfo.MEMBER_NOT_FOUND));
    }

    public MemberCreateParam findOne(Long id) {
        return memberRepository.findById(id)
                .map(memberConverter::convertMemberToSaveDto)
                .orElseThrow(() -> new BusinessException(ErrorInfo.MEMBER_NOT_FOUND));
    }

    public Page<MemberCreateParam> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable).map(memberConverter::convertMemberToSaveDto);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member foundMember = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ErrorInfo.MEMBER_NOT_FOUND));
        memberRepository.delete(foundMember);
    }

    public EmailCheckResult checkMemberEmail(String email) {
        return memberConverter.convertIsExistedEmailToEmailCheckResult(memberRepository.existsByEmail(email));
    }

    public NicknameCheckResult checkMemberNickname(String nickname) {
        return memberConverter.convertIsExistedNicknameToNicknameCheckResult(memberRepository.existsByNickName(nickname));
    }

    public boolean authenticateMember(Long memberId, String email, String password) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ErrorInfo.MEMBER_NOT_FOUND));
        return member.getEmail().equals(email) && member.getPassword().equals(password);
    }

}
