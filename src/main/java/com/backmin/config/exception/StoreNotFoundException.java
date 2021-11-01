package com.backmin.config.exception;

import com.backmin.domains.common.enums.ErrorInfo;

public class StoreNotFoundException extends BusinessException {

    public StoreNotFoundException() {
        super(ErrorInfo.STORE_NOT_FOUND);
    }
}
