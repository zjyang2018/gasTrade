/**
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: cmbfae.com</p>
 */
package com.common.exception;

/**
 * 文件名称： DaoException.java 功能说明：dao层Exception类 开发人员： liangqf002 开发时间：2015年8月21日
 * 下午4:39:42 修改记录：修改日期 修改人员 修改说明
 */
public class DaoException extends BaseException {

	private static final long serialVersionUID = 7196812223986179562L;

	public DaoException() {

	}

	public DaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super( message, cause, enableSuppression, writableStackTrace );
	}

	public DaoException(String message, Throwable cause) {
		super( message, cause );
	}

	public DaoException(String message) {
		super( message );
	}

	public DaoException(Throwable cause) {
		super( cause );
	}
}
