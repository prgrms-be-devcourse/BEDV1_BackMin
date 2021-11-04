package com.backmin.domains.member.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateParam {

    @NotNull
    private Long id;

    private String phoneNumber;

    private String nickName;

    private String address;

    private String email;

    private String password;

}
