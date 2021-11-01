package com.backmin.domains.common.exception;

import com.backmin.domains.common.enums.ErrorInfo;

public class StoreNotFoundException extends BusinessException {

    public StoreNotFoundException() {
        super(ErrorInfo.STORE_NOT_FOUND);
    }
}
