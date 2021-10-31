package com.backmin.domains.member.controller;

import com.backmin.domains.common.dto.ApiResult;
import com.backmin.domains.member.dto.MemberCreateRequest;
import com.backmin.domains.member.dto.MemberUpdateRequest;
import com.backmin.domains.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
@RequestMapping(path = "/api/v1/bm/members", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

    private final MemberService memberService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult createMember(@RequestBody @Valid MemberCreateRequest memberCreateRequest) {
        memberService.save(memberCreateRequest);
        return ApiResult.ok(null);
    }

    @PutMapping(value = "{/id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult updateMember(@PathVariable("id") Long memberId, @RequestBody @Valid MemberUpdateRequest memberUpdateRequest) {
        memberService.update(memberId, memberUpdateRequest);
        return ApiResult.ok(null);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<Object> deleteMember(@PathVariable("id") Long memberId) {
        memberService.deleteMember(memberId);
        return ApiResult.ok(null);
    }

    @GetMapping("/email/{email}")
    public ApiResult emailCheck(@PathVariable String email) {
        return ApiResult.ok(memberService.checkMemberEmail(email));
    }

    @GetMapping("/nickname/{nickname}")
    public ApiResult nickNameCheck(@PathVariable String nickname) {
        return ApiResult.ok(memberService.checkMemberNickname(nickname));
    }

}
