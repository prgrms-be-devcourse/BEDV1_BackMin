package com.backmin.domains.member.domain;

import com.backmin.domains.common.BaseEntity;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "members",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_member_id", columnNames = "member_id"),
                @UniqueConstraint(name = "UK_member_email", columnNames = "email")
        })
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(name = "email", length = 50, nullable = false)
    @Pattern(regexp = "([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}", message = "입력된 이메일 형식이 올바르지 않습니다.")
    private String email;

    @Column(name = "password", length = 50, nullable = false)
    private String password;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "nick_name", length = 20, nullable = false)
    private String nickName;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Builder
    public Member(Long id, String email, String password, String phoneNumber, String nickName, String address) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.address = address;
    }

    public static Member of(String email, String password, String phoneNumber, String nickName, String address) {
        return Member.builder()
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .nickName(nickName)
                .address(address)
                .build();
    }

    /**
     * todo: validation 을 더 체크할 것
     */
    public void updateInfo(String nickName, String phoneNumber, String address) {
        validationNickName(nickName);
        validationPhoneNumber(phoneNumber);
        validationAddress(address);
    }

    private void validationNickName(String nickName) {
        if (Objects.nonNull(nickName)) {
            this.nickName = nickName;
        }
    }

    private void validationPhoneNumber(String phoneNumber) {
        if (Objects.nonNull(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        }
    }

    private void validationAddress(String address) {
        if (Objects.nonNull(address)) {
            this.address = address;
        }
    }

}
