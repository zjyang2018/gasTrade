package com.common.exception;

import com.common.enums.ErrorLevel;

public class SystemException extends Exception {

	private static final long	serialVersionUID	= 7151080550802600458L;

	private ErrorLevel			errorLevel;

	private String				errorCode;

	private String				errorMessage;

	public ErrorLevel getErrorLevel() {
		return errorLevel;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public SystemException(ErrorLevel errorLevel, String errorCode, String errorMessage) {
		super( errorMessage );
		this.errorLevel = errorLevel;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
