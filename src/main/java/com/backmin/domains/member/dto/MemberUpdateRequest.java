package com.backmin.domains.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberUpdateRequest {

    private Long id;

    private String phoneNumber;

    private String nickName;

    private String address;

    public static MemberUpdateRequest of(Long id,
            String phoneNumber,
            String nickName,
            String address
    ) {
        MemberUpdateRequest memberUpdateRequest = new MemberUpdateRequest();
        memberUpdateRequest.setId(id);
        memberUpdateRequest.setPhoneNumber(phoneNumber);
        memberUpdateRequest.setNickName(nickName);
        memberUpdateRequest.setAddress(address);

        return memberUpdateRequest;
    }

}
