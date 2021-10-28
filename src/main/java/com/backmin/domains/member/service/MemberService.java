package com.backmin.domains.member.service;

import com.backmin.domains.member.converter.MemberConverter;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.member.dto.MemberCreateRequest;
import com.backmin.domains.member.dto.MemberUpdateRequest;
import com.backmin.domains.member.vo.Nickname;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.Email;
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
    public Long save(MemberCreateRequest memberSaveDto) {
        checkMemberEmail(memberSaveDto);
        checkMemberNickname(memberSaveDto);
        return memberRepository.save(memberConverter.convertSaveDtoToMember(memberSaveDto)).getId();
    }

    @Transactional
    public Long update(Long memberId, MemberUpdateRequest memberUpdateRequest) {
        return memberRepository.findById(memberId)
                .map(member -> memberConverter.convertUpdateDtoToMember(memberUpdateRequest))
                .orElseThrow(() -> new RuntimeException("Not Found Member Id : " + memberUpdateRequest.getId())).getId();
    }

    @Transactional
    public MemberCreateRequest findOne(Long id) {
        return memberRepository.findById(id)
                .map(memberConverter::convertMemberToSaveDto)
                .orElseThrow(() -> new RuntimeException("Not Found Member Id : " + id));
    }

    @Transactional
    public Page<MemberCreateRequest> findAll(Pageable pageable) {
        PageRequest.of(10, 10);
        return memberRepository.findAll(pageable).map(memberConverter::convertMemberToSaveDto);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member deleteMember = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Not Found Member Id : " + memberId));
        memberRepository.delete(deleteMember);
    }

    public void checkMemberEmail(MemberCreateRequest memberCreateRequest) {
        Optional<Member> findMemberByEmail = memberRepository.findByEmail(memberCreateRequest.getEmail());
        findMemberByEmail.ifPresent(member -> {
                throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        });
    }

    public void checkMemberNickname(MemberCreateRequest memberCreateRequest) {
        Optional<Member> findMemberByNickname = memberRepository.findByNickName(memberCreateRequest.getNickName());
        findMemberByNickname.ifPresent(member -> {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        });
    }

}
