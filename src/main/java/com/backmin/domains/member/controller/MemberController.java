package com.backmin.domains.member.controller;

import com.backmin.config.exception.BusinessException;
import com.backmin.domains.common.dto.ApiResult;
import com.backmin.domains.common.enums.ErrorInfo;
import com.backmin.domains.member.dto.request.MemberCreateParam;
import com.backmin.domains.member.dto.request.MemberUpdateParam;
import com.backmin.domains.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/bm/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult createMember(@RequestBody @Valid MemberCreateParam memberCreateParam) {
        memberService.save(memberCreateParam);
        return ApiResult.ok();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult updateMember(@PathVariable("id") Long memberId, @RequestBody @Valid MemberUpdateParam memberUpdateParam) {
        if (memberService.authenticateMember(memberId, memberUpdateParam.getEmail(), memberUpdateParam.getPassword())) {
            memberService.update(memberId, memberUpdateParam);
            return ApiResult.ok();
        }
        throw new BusinessException(ErrorInfo.INCORRECT_MEMBER_SECURITY);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult deleteMember(@PathVariable("id") Long memberId) {
        memberService.deleteMember(memberId);
        return ApiResult.ok();
    }

    @GetMapping(value = "/email/{email}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult emailCheck(@PathVariable String email) {
        return ApiResult.ok(memberService.checkMemberEmail(email));
    }

    @GetMapping(value = "/nickname/{nickname}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult nickNameCheck(@PathVariable String nickname) {
        return ApiResult.ok(memberService.checkMemberNickname(nickname));
    }

}
