package com.backmin.domains.member.domain;

import com.backmin.domains.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Email
    @Column(name = "email", length = 30, nullable = false)
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

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    public void changePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

}
