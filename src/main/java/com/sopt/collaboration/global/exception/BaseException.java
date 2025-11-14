package com.sopt.collaboration.global.exception;

import com.sopt.collaboration.global.response.error.ErrorCode;
import com.sopt.collaboration.global.response.error.ErrorType;

public class BaseException extends RuntimeException {
	private final ErrorType errorCode;

	public BaseException(ErrorType errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public BaseException(ErrorCode errorCode, String detail) {
		super(errorCode.getMessage() + " → " + detail);
		this.errorCode = errorCode;
	}

	public ErrorType getErrorCode() {
		return errorCode;
	}
}
