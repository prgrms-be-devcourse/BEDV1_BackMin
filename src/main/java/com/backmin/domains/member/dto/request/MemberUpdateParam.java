package com.backmin.domains.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateParam {

    private Long id;

    private String phoneNumber;

    private String nickName;

    private String address;

    public static MemberUpdateParam of(Long id,
            String phoneNumber,
            String nickName,
            String address
    ) {
        MemberUpdateParam memberUpdateParam = new MemberUpdateParam();
        memberUpdateParam.setId(id);
        memberUpdateParam.setPhoneNumber(phoneNumber);
        memberUpdateParam.setNickName(nickName);
        memberUpdateParam.setAddress(address);

        return memberUpdateParam;
    }

}
