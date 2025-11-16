package com.sopt.collaboration.global.response.success;

import lombok.Getter;

@Getter
public enum CategorySuccessCode implements SuccessType {

    CATEGORY_RETRIEVED("CAT001", "카테고리 조회 성공");

    private final String code;
    private final String message;

    CategorySuccessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
