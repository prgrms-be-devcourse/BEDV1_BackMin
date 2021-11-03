package com.backmin.domains.common.enums;

import lombok.Getter;

@Getter
public enum ErrorInfo {

    METHOD_ARG_NOT_VALID("METHOD_ARG_NOT_VALID", ""),
    UNKNOWN("UNKNOWN", "서버 에러로 인해 데이터를 로드 할 수 없습니다."),
    STORE_NOT_FOUND("STORE_NOT_FOUND", "해당 가게를 찾을 수 없습니다."),
    MENU_NOT_FOUND("MENU_NOT_FOUND", "해당 메뉴를 찾을 수 없습니다."),
    MENU_OPTION_NOT_FOUND("MENU_OPTION_NOT_FOUND", "해당 메뉴 옵션을 찾을 수 없습니다."),
    ORDER_STATUS_AUTHORITY("ORDER_STATUS_AUTHORITY","고객은 주문을 수락할 수 없습니다."),
    NOT_FOUND("NOT_FOUND", "해당 데이터를 찾을 수 없습니다."),
    MEMBER_NOT_FOUND("MEMBER_NOT_FOUND", "해당 멤버를 찾을 수 없습니다."),
    REVIEW_NOT_FOUND("REVIEW_NOT_FOUND", "해당 리뷰를 찾을 수 없습니다."),
    ORDER_NOT_FOUND("ORDER_NOT_FOUND", "해당 주문을 찾을 수 없습니다."),
    DUPLICATE_EMAIL("DUPLICATE_EMAIL", "이미 등록된 이메일입니다."),
    DUPLICATE_NICKNAME("DUPLICATE_NICKNAME", "이미 등록된 닉네임입니다."),
    INCORRECT_MEMBER_SECURITY("INCORRECT_MEMBER_SECURITY", "회원정보와 일치하지 않습니다.");

    ErrorInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

}
