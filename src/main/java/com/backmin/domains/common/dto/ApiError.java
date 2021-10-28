package com.backmin.domains.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiError {

    private String code;

    private Object message;

    @Builder
    public ApiError(String code, Object message) {
        this.code = code;
        this.message = message;
    }

}
