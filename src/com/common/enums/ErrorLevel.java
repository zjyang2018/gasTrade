package com.common.enums;

/**
 * 
 * @author Feng 故障程度举类
 */
public enum ErrorLevel {

	FATAL_ERROR("E", "致命错误,需人工处理修复"), TIP_ERROR("T", "提示性错误，无需人工介入处理"), PARAM_ERROR("P", "请求调用参数错误");

	private String	code;

	private String	message;

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	private ErrorLevel(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
