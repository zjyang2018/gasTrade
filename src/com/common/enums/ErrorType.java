package com.common.enums;

/**
 * @author Feng ：错误类型枚举类
 */
public enum ErrorType {

	SYSTEM_ERROR("T", "系统故障"), BUSINESS_ERROR("B", "业务故障");

	private String	code;

	private String	message;

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	private ErrorType(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
