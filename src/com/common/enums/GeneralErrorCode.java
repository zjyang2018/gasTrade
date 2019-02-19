package com.common.enums;

/**
 * 
 * @author Feng 通用错误码枚举类
 */
public enum GeneralErrorCode {
	// 系统处理成功
	PROCESS_SUCCESS("0000", "系统处理成功"),

	// 系统已经处理
	PROCESS_YET("0001", "交易订单%s已经处理"),

	// 传入参数不能为空
	INPUT_PARAM_NULL("1001", "%s不能为空"),
	// 传入参数格式不正确
	INPUT_PARAM_FORMAT_ERROR("1002", "%s格式不正确"),
	// 解释报文错误
	EXPLAIN_MESSAGE_ERROR("1005", "解析报文错误"),
	// 报文格式错误
	MESSAGE_FORMAT_ERROR("1006", "报文格式错误"),
	// 日期格式不正确
	DATE_FORMAT_ERROR("1009", "日期格式不正确"),
	// 业务逻辑错误
	BUSINESS_LOGICAL_ERROR("1010", "业务逻辑错误"),

	// 系统处理异常,包括未知异常
	SYSTEM_PROCESS_ERROR("9999", "系统内部处理异常");

	private String	code;

	private String	message;

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	private GeneralErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
