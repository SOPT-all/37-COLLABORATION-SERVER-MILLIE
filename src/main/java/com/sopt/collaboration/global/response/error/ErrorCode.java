package com.sopt.collaboration.global.response.error;

import lombok.Getter;

@Getter
public enum ErrorCode implements ErrorType {

	// 공통 에러
	INVALID_INPUT("C001", "입력값이 올바르지 않습니다", 400),
	INVALID_FORMAT("C002", "데이터 형식이 올바르지 않습니다", 400),

	// 리뷰 관련 에러
	REVIEW_NOT_FOUND("R001", "리뷰를 찾을 수 없습니다", 404),

	INTERNAL_SERVER_ERROR("C999", "서버 내부 오류가 발생했습니다", 500);

	private final String code;
	private final String message;
	private final int status;

	ErrorCode(String code, String message, int status) {
		this.code = code;
		this.message = message;
		this.status = status;
	}
}
