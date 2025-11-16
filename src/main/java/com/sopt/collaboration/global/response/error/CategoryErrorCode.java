package com.sopt.collaboration.global.response.error;

import lombok.Getter;

@Getter
public enum CategoryErrorCode implements ErrorType {

    CATEGORY_NOT_FOUND("CAT404", "카테고리를 찾을 수 없습니다", 404);

    private final String code;
    private final String message;
    private final int status;

    CategoryErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
