package com.backmin.domains.common.exception;

import com.backmin.domains.common.enums.ErrorInfo;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private ErrorInfo errorInfo;

    public BusinessException(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }
}
