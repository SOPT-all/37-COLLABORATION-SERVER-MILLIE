package com.sopt.collaboration.global.response.success;

import lombok.Getter;

@Getter
public enum SuccessCode implements SuccessType {
	SUCCESS("S200", "성공"),

	// 리뷰 관련 성공
	REVIEW_LIKE_TOGGLED("R001", "리뷰 좋아요가 업데이트되었습니다");

	private final String code;
	private final String message;

	SuccessCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
