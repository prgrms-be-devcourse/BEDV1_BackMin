package com.backmin.domains.member.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberCreateParam {

    private String email;

    private String password;

    private String phoneNumber;

    private String nickName;

    private String address;

}
