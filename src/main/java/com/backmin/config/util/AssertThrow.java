package com.backmin.config.util;

import com.backmin.config.exception.BusinessException;
import com.backmin.domains.common.enums.ErrorInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssertThrow {

    public static void isTrue(boolean isTrue, ErrorInfo errorInfo) {
        if (isTrue) {
            throw new BusinessException(errorInfo);
        }
    }

}
