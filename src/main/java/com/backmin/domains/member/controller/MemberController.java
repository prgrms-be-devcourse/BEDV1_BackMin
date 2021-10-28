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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
@RequestMapping("api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<Object> createMember(@RequestBody @Valid MemberCreateRequest memberCreateRequest) {
        final Long createdMemberId = memberService.save(memberCreateRequest);
        return ApiResult.ok(createdMemberId);
    }

    @PatchMapping(value = "{/id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<Long> updateMember(@PathVariable("id") Long memberId, @RequestBody @Valid MemberUpdateRequest memberUpdateRequest) {
        final Long updatedMemberId = memberService.update(memberId, memberUpdateRequest);
        return ApiResult.ok(updatedMemberId);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<Object> deleteMember(@PathVariable("id") Long memberId) {
        memberService.deleteMember(memberId);
        return ApiResult.ok(null);
    }

}
