package com.sopt.collaboration.global.response.success;

import lombok.Getter;

@Getter
public enum SuccessCode implements SuccessType {

	// 공통 응답 코드
	SUCCESS("S200", "성공"),

	// 리뷰 관련 성공
	REVIEW_LIKE_TOGGLED("R001", "리뷰 좋아요가 업데이트되었습니다"),

    // 카테고리 응답 코드
    CATEGORY_RETRIEVED("C201", "카테고리 조회 성공");

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
