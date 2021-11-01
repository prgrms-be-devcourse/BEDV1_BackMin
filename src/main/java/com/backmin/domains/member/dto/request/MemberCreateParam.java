package com.backmin.domains.member.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberCreateParam {

    private Long id;

    @NotBlank(message = "가입하실 이메일을 입력해주세요.")
    @Length(max = 50, message = "이메일 주소는 50자를 넘을 수 없습니다.")
    @Pattern(regexp = "([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(max = 50, message = "비밀번호는 50자를 넘을 수 없습니다.")
    private String password;

    @NotBlank(message = "연락처를 입력해주세요.")
    @Length(max = 13, message = "연락처는 '-'을 포함하여 13자를 넘을 수 없습니다.")
    private String phoneNumber;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Length(max = 10, message = "닉네임은 10자를 넘을 수 없습니다.")
    private String nickName;

    @NotBlank(message = "주소를 입력해주세요.")
    @Length(max = 100, message = "주소는 100자를 넘을 수 없습니다.")
    private String address;

    public static MemberCreateParam of(Long id,
            String email,
            String password,
            String phoneNumber,
            String nickName,
            String address
    ) {
        MemberCreateParam memberCreateParam = new MemberCreateParam();
        memberCreateParam.setId(id);
        memberCreateParam.setEmail(email);
        memberCreateParam.setPassword(password);
        memberCreateParam.setPhoneNumber(phoneNumber);
        memberCreateParam.setNickName(nickName);
        memberCreateParam.setAddress(address);

        return memberCreateParam;
    }
}
