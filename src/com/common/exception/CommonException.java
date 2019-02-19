package com.common.exception;

/**
 * 本类为：公共处理异常
 */
public class CommonException extends Exception {

	private static final long	serialVersionUID	= 7151080550802600458L;

	private String				message;

	public String getMessage() {
		return message;
	}

	public CommonException(String message) {
		super( message );
		this.message = message;
	}
}
