package com.sopt.collaboration.global.exception;

import com.sopt.collaboration.global.response.error.ErrorType;

public class CategoryException extends BaseException {

    public CategoryException(ErrorType errorType) {
        super(errorType);
    }
}
