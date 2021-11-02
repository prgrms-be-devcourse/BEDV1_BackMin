package com.backmin.domains.member.domain;

import com.backmin.domains.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
    public Member(Long id,
            String email,
            String password,
            String phoneNumber,
            String nickName,
            String address
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.address = address;
    }

    public static Member of(Long id,
            String email,
            String password,
            String phoneNumber,
            String nickName,
            String address
    ) {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .nickName(nickName)
                .address(address)
                .build();
    }

    public void updateInfo(String nickName, String phoneNumber, String address) {
        if (!(nickName.isBlank())) {
            this.nickName = nickName;
        }

        if (!(phoneNumber.isBlank())) {
            this.phoneNumber = phoneNumber;
        }

        if (!(address.isBlank())) {
            this.address = address;
        }
    }

}
