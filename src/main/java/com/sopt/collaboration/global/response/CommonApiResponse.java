package com.sopt.collaboration.global.response;

import com.sopt.collaboration.global.response.error.ErrorType;
import com.sopt.collaboration.global.response.success.SuccessType;

import io.swagger.v3.oas.annotations.media.Schema;

public class CommonApiResponse<T> {

	@Schema(description = "응답 코드", example = "M001")
	private final String code;

	@Schema(description = "응답 메시지", example = "## 작업을 성공했습니다")
	private final String message;

	@Schema(description = "응답 데이터", nullable = true)
	private final T data;

	private CommonApiResponse(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static <T> CommonApiResponse<T> success(SuccessType successCode, T data) {
		return new CommonApiResponse<>(successCode.getCode(), successCode.getMessage(), data);
	}

	public static CommonApiResponse<Void> success(SuccessType successCode) {
		return new CommonApiResponse<>(successCode.getCode(), successCode.getMessage(), null);
	}

	public static CommonApiResponse<Void> fail(ErrorType errorType) {
		return new CommonApiResponse<>(errorType.getCode(), errorType.getMessage(), null);
	}

	public static <T> CommonApiResponse<T> fail(ErrorType errorType, T details) {
		return new CommonApiResponse<>(errorType.getCode(), errorType.getMessage(), details);
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}
}
