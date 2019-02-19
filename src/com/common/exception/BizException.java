package com.common.exception;

public class BizException extends RuntimeException {

	private static final long	serialVersionUID	= 1L;
	// 错误码
	private String				errorCode;
	// 错误描述
	private String				errorMsg;

	public BizException(String errorMsg) {
		super( errorMsg );
		this.errorMsg = errorMsg;
	}

	public BizException(String errorCode, String errorMsg) {
		super( errorMsg );
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
