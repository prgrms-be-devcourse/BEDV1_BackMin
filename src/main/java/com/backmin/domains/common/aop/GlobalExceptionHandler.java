package com.backmin.domains.common.aop;

import com.backmin.domains.common.dto.ApiResult;
import com.backmin.domains.common.enums.ErrorInfo;
import com.backmin.domains.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("{}", ex);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
            .forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));

        return ApiResult.error("METHOD_ARG_NOT_VALID", errors);
    }

    @ExceptionHandler(BusinessException.class)
    public ApiResult<?> handleBusinessExceptionException(BusinessException ex) {
        log.error("{}", ex);

        ErrorInfo errorInfo = ex.getErrorInfo();

        return ApiResult.error(errorInfo.getCode(), errorInfo.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResult handleException(Exception ex) {
        log.error("{}", ex);

        ErrorInfo errorInfo = ErrorInfo.UNKNOWN;

        return ApiResult.error(errorInfo.getCode(), errorInfo.getMessage());
    }

}
