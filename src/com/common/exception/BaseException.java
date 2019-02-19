/**
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: cmbfae.com</p>
 */
package com.common.exception;

/**
 * 文件名称： BaseException.java 功能说明： uncheck exceoption基类 开发人员： liangqf002
 * 开发时间：2015年8月21日 下午4:32:47 修改记录：修改日期 修改人员 修改说明
 */
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 2064969871179938440L;

	public BaseException() {

	}

	public BaseException(String message) {
		super( message );
	}

	public BaseException(String message, Throwable th) {
		super( message, th );
	}

	public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super( message, cause, enableSuppression, writableStackTrace );
	}

	public BaseException(Throwable th) {
		super( th );
	}

	public String getMessage() {
		return super.getMessage();
	}
}
