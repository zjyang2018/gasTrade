package com.common.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.common.exception.BizException;

/**
 * 通用校验器
 * 
 * @author wangjingao
 *
 */
public class ValidatorHelper {
	public static final String	EMAIL_REG_EXP	= "^[a-zA-Z0-9_\\-\\.]{1,}@[a-zA-Z0-9_\\-]{1,}\\.[a-zA-Z0-9_\\-.]{1,}$";

	public static final String	MOBILE_REG_EXP	= "(18\\d{9}$)|(13\\d{9}$)|(15\\d{9}$)";

	public static final String	CHINESE_REG_EXP	= "[\u4e00-\u9fa5]";

	public static void checkUrl(String value, String errMsg) throws BizException {
		try {
			new URL( value );
		} catch (MalformedURLException e) {
			throw new BizException( errMsg );
		}
	}

	/**
	 * 检查是否为空
	 * 
	 * @param value
	 * @param errMsg
	 * @throws BizException
	 */
	public static void checkStringNullOrEmpty(String value, String errMsg) throws BizException {
		if (value == null || value.equals( "" )) {
			throw new BizException( errMsg );
		}
	}

	public static void checkNotChinese(String str, String errMsg) {
		if (isChineseChar( str )) {
			throw new BizException( errMsg );
		}
	}

	public static void checkChinese(String str, String errMsg) {
		if (!isChineseChar( str )) {
			throw new BizException( errMsg );
		}
	}

	public static boolean isChineseChar(String str) {

		if (str == null)
			return false;

		boolean temp = false;
		Pattern p = Pattern.compile( CHINESE_REG_EXP );
		Matcher m = p.matcher( str );
		if (m.find()) {
			temp = true;
		}
		return temp;
	}

	/**
	 * 检查时间格式是否正确
	 * 
	 * @param value
	 * @param errMsg
	 * @throws BizException
	 */
	public static void checkDateFormat(String value, String errMsg) throws BizException {
		try {
			if (StringUtils.length( value ) != 14) {
				throw new BizException( errMsg );
			}
			SimpleDateFormat df = new SimpleDateFormat( "yyyyMMddHHmmss" );
			df.parse( value );
		} catch (ParseException e) {
			throw new BizException( errMsg );
		}
	}

	/**
	 * 可传参数的时间格式检查
	 * 
	 * @param value
	 * @param parseStr
	 * @param errMsg
	 * @throws BizException
	 */
	public static void checkDateFormat(String value, String parseStr, String errMsg) throws BizException {
		try {
			SimpleDateFormat df = new SimpleDateFormat( parseStr );
			df.parse( value );
		} catch (ParseException e) {
			throw new BizException( errMsg );
		}
	}

	/**
	 * 检查对象是否空
	 * 
	 * @param obj
	 * @param errMsg
	 * @throws BizException
	 */
	public static void checkObjectNull(Object obj, String errMsg) throws BizException {
		if (obj == null) {
			throw new BizException( errMsg );
		}
	}

	/**
	 * 检查对象是否空
	 * 
	 * @param obj
	 * @param errMsg
	 * @throws BizException
	 */
	public static void checkObjectNull(Object obj, String errCode, String errMsg) throws BizException {
		if (obj == null) {
			throw new BizException( errCode, errMsg );
		}
	}

	/**
	 * 检查对象数据是否为空
	 * 
	 * @param obj
	 * @param errMsg
	 * @throws BizException
	 */
	public static void checkObjectArrayEmpty(Object[] obj, String errMsg) throws BizException {
		if (obj == null || obj.length == 0) {
			throw new BizException( errMsg );
		}
	}

	/**
	 * 检查金额格式是否正确
	 * 
	 * @param value
	 * @param errMsg
	 * @throws BizException
	 */
	public static void checkAmount(String value, String errMsg) throws BizException {
		try {
			BigDecimal amt = new BigDecimal( value );
			if (amt.precision() > 12 || amt.scale() > 2) {
				throw new BizException( errMsg );
			}
			if (amt.compareTo( BigDecimal.ZERO ) < 0) {
				throw new BizException( errMsg );
			}
		} catch (NumberFormatException e) {
			throw new BizException( errMsg );
		}

	}

	/**
	 * 检查金额是否大于零
	 * 
	 * @param value
	 * @param errMsg
	 * @throws BizException
	 */
	public static void checkPositiveAmount(String value, String errMsg) throws BizException {
		try {
			BigDecimal amt = new BigDecimal( value );
			if (amt.precision() > 12 || amt.scale() > 2) {
				throw new BizException( errMsg );
			}
			if (amt.compareTo( BigDecimal.ZERO ) < 1) {
				throw new BizException( errMsg );
			}
		} catch (NumberFormatException e) {
			throw new BizException( errMsg );
		}

	}

	/**
	 * 检查是否是整数类型
	 * 
	 * @param value
	 * @param errMsg
	 * @throws BizException
	 */
	public static void checkInteger(String value, String errMsg) throws BizException {
		try {
			Long.valueOf( value );
		} catch (NumberFormatException e) {
			throw new BizException( errMsg );
		}
	}

	/**
	 * 检查是否是正整数类型
	 * 
	 * @param value
	 * @param errMsg
	 * @throws BizException
	 */
	public static void checkPositiveInteger(String value, String errMsg) throws BizException {
		try {
			if (Long.valueOf( value ) <= 0) {
				throw new BizException( errMsg );
			}
		} catch (NumberFormatException e) {
			throw new BizException( errMsg );
		}
	}

	public static void checkStringLength(String value, int minLen, int maxLen, String errMsg) throws BizException {
		try {
			if (value.getBytes( "utf-8" ).length < minLen || value.getBytes( "utf-8" ).length > maxLen) {
				throw new BizException( errMsg );
			}
		} catch (UnsupportedEncodingException e) {
			throw new BizException( errMsg );
		}
	}

	/**
	 * 检查如果不为空，参数长度是否正确
	 * 
	 * @param value
	 * @param minLen
	 * @param maxLen
	 * @param errMsg
	 * @throws BizException
	 */
	public static void checkNotNullStringLength(String value, int minLen, int maxLen, String errMsg) throws BizException {
		if (StringUtils.isBlank( value )) {
			return;
		}
		try {
			if (value.getBytes( "utf-8" ).length < minLen || value.getBytes( "utf-8" ).length > maxLen) {
				throw new BizException( errMsg );
			}
		} catch (UnsupportedEncodingException e) {
			throw new BizException( errMsg );
		}
	}

	/**
	 * 检查两个参数是否相等
	 */
	public static void checkEquals(String value1, String value2, String errMsg) throws BizException {
		if (!value1.equals( value2 )) {
			throw new BizException( errMsg );
		}
	}

	/**
	 * 检查两个参数是否相等
	 */
	public static void checkEquals(String value1, String value2, String errorCode, String errMsg) throws BizException {
		if (!value1.equals( value2 )) {
			throw new BizException( errorCode, errMsg );
		}
	}

	public static void checkNotEquals(String value1, String value2, String errMsg) throws BizException {
		if (value1.equals( value2 )) {
			throw new BizException( errMsg );
		}
	}

	/**
	 * 检查参数是否在预设的值列表之内
	 * 
	 * @param errMsg
	 *            错误信息
	 * @param value
	 *            待比较的值
	 * @param values
	 *            预设的值列表
	 */
	public static void checkContains(String errMsg, String value, String... values) throws BizException {
		boolean result = false;
		for (String v : values) {
			if (v.equals( value )) {
				result = true;
				break;
			}
		}

		if (!result) {
			throw new BizException( errMsg );
		}
	}

	/**
	 * 检查多个参数同时为空
	 */
	public static void checkSameTimeNull(String errMsg, String... values) throws BizException {
		int j = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[ i ] != null && values[ i ].length() > 0) {
				j++;
			}
		}

		if (j == 0) {
			throw new BizException( errMsg );
		}

	}

	/**
	 * 检查字符串格式是否合法
	 * 
	 * @param value
	 *            待检查的字符串
	 * @param regExp
	 *            检查格式用的正则表达式
	 * @param errMsg
	 *            验证失败错误消息
	 * @return <code>true</code>如果email格式合法，否则返回<code>false</code>
	 */
	public static void isValid(String value, String regExp, String errMsg) throws BizException {
		if (value != null && value.length() > 0) {
			Pattern pattern = Pattern.compile( regExp );
			if (!pattern.matcher( value ).matches()) {
				throw new BizException( errMsg );
			}
		}
	}

	public static void checkTime(String startTime, String endTime, String dateFormat, String errMsg) throws BizException {
		if (StringUtils.isBlank( startTime ) || StringUtils.isBlank( endTime )) {
			throw new BizException( errMsg );
		}
		SimpleDateFormat df = new SimpleDateFormat( dateFormat );
		try {
			Date startDate = df.parse( startTime );
			Date endDate = df.parse( endTime );
			if (!startDate.before( endDate )) {
				throw new BizException( errMsg );
			}
		} catch (ParseException e) {
			throw new BizException( errMsg );
		}
	}

	/**
	 * 方法用途: 校验是否为true<br>
	 * 实现步骤: <br>
	 * 
	 * @param flag
	 *            校验目标
	 * @param errMsg
	 *            错误提示
	 */
	public static void checkTrue(boolean flag, String errMsg) throws BizException {
		if (!flag) {
			throw new BizException( errMsg );
		}
	}

	/**
	 * 方法用途: 校验是否为true<br>
	 * 实现步骤: <br>
	 * 
	 * @param flag
	 *            校验目标
	 * @param errMsg
	 *            错误提示
	 */
	public static void checkTrue(boolean flag, String errorCode, String errMsg) throws BizException {
		if (!flag) {
			throw new BizException( errorCode, errMsg );
		}
	}
}
