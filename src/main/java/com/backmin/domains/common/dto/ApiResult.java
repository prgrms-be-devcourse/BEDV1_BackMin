package com.backmin.domains.common.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResult<T> {

    private boolean success;

    private T data;

    private LocalDateTime serverDatetime;

    @Builder
    public ApiResult(boolean success, T data) {
        this.success = success;
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    public static <T> ApiResult<T> ok(T response) {
        return new ApiResult<>(true, response);
    }

    public static ApiResult error(String code, Object message) {
        return new ApiResult(false, new ApiError(code, message));
    }

}
