package com.backmin.config.exception;

import com.backmin.domains.common.dto.ApiResult;
import com.backmin.domains.common.enums.ErrorInfo;
import com.backmin.config.exception.BusinessException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("{}", ex); /** todo: 에러 로그를 얼마나 찍어야하는지? */
        return ApiResult.error("METHOD_ARG_NOT_VALID", ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(error -> ((FieldError) error).getField(), ObjectError::getDefaultMessage)));
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> handleBusinessExceptionException(BusinessException ex) {
        log.error("{}", ex);
        ErrorInfo errorInfo = ex.getErrorInfo();
        return ApiResult.error(errorInfo.getCode(), errorInfo.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleException(Exception ex) {
        log.error("{}", ex);
        ErrorInfo errorInfo = ErrorInfo.UNKNOWN;
        return ApiResult.error(errorInfo.getCode(), errorInfo.getMessage());
    }

}
