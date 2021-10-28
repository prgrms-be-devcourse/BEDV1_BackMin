package com.backmin.domains.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    private Long id;

    private String email;

    private String password;

    private String phoneNumber;

    private String nickName;

    private String address;

}
