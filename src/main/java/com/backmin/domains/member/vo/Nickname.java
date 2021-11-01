package com.backmin.domains.member.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Nickname{

    private String nickName;

    public Nickname(String nickName) {
        this.nickName = nickName;
    }
}