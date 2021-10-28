package com.backmin.domains.common.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
public class ApiResult<T> {

    private boolean success;

    private T data;

    private LocalDateTime serverDatetime;

    private ApiError error;

    @Builder
    public ApiResult(boolean success, T data, ApiError error) {
        this.success = success;
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
        this.error = error;
    }

    public static <T>ApiResult<T> ok(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult<?> error(String code, Object message) {
        return new ApiResult<>(false, null, new ApiError(code, message));
    }

}
