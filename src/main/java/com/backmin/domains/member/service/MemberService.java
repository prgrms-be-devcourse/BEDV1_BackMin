package com.backmin.domains.member.service;

import com.backmin.domains.member.converter.MemberConverter;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.member.dto.EmailCheckRequest;
import com.backmin.domains.member.dto.MemberCreateRequest;
import com.backmin.domains.member.dto.MemberUpdateRequest;
import com.backmin.domains.member.dto.NicknameCheckRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberConverter memberConverter;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberCreateRequest memberCreateRequest) {
        EmailCheckRequest checkEmail = checkMemberEmail(memberCreateRequest.getEmail());
        NicknameCheckRequest checkNickname = checkMemberNickname(memberCreateRequest.getNickName());
        if(checkEmail.isDuplication())
            throw new IllegalArgumentException("이미 중복된 이메일이 있습니다.");
        if(checkNickname.isDuplication())
            throw new IllegalArgumentException("이미 중복된 닉네임이 있습니다.");
        memberRepository.save(memberConverter.convertSaveDtoToMember(memberCreateRequest));
    }

    @Transactional
    public void update(Long memberId, MemberUpdateRequest memberUpdateRequest) {
        memberRepository.findById(memberId)
                .map(member -> memberConverter.convertUpdateDtoToMember(member, memberUpdateRequest))
                .orElseThrow(() -> new RuntimeException("Not Found Member Id : " + memberUpdateRequest.getId()));
    }

    @Transactional(readOnly = true)
    public MemberCreateRequest findOne(Long id) {
        return memberRepository.findById(id)
                .map(memberConverter::convertMemberToSaveDto)
                .orElseThrow(() -> new RuntimeException("Not Found Member Id : " + id));
    }

    @Transactional(readOnly = true)
    public Page<MemberCreateRequest> findAll(Pageable pageable) {
        PageRequest.of(10, 10);
        return memberRepository.findAll(pageable).map(memberConverter::convertMemberToSaveDto);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member deleteMember = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Not Found Member Id : " + memberId));
        memberRepository.delete(deleteMember);
    }

    public EmailCheckRequest checkMemberEmail(String email) {
        EmailCheckRequest emailCheckRequest = new EmailCheckRequest();
        emailCheckRequest.setDuplication(memberRepository.existsByEmail(email));
        return emailCheckRequest;
    }

    public NicknameCheckRequest checkMemberNickname(String nickname) {
        NicknameCheckRequest nicknameCheckRequest = new NicknameCheckRequest();
        nicknameCheckRequest.setDuplication(memberRepository.existsByNickName(nickname));
        return nicknameCheckRequest;
    }

}
