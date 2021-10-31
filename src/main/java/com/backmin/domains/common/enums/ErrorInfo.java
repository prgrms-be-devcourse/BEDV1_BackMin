package com.backmin.domains.common.enums;

import lombok.Getter;

@Getter
public enum ErrorInfo {

    METHOD_ARG_NOT_VALID("METHOD_ARG_NOT_VALID", ""),
    UNKNOWN("UNKNOWN", "서버 에러로 인해 데이터를 로드 할 수 없습니다."),
    STORE_NOT_FOUND("STORE_NOT_FOUND", "해당 가게를 찾을 수 없습니다.");

    ErrorInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

}
