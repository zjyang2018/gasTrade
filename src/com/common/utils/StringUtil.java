/**
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: cmbfae.com</p>
 */
package com.common.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 文件名称： StringUtil.java 功能说明： String工具类 开发人员： liangqf002 开发时间：2015年8月20日
 * 上午11:00:48 修改记录：修改日期 修改人员 修改说明
 */
public class StringUtil {

	private static final Logger		logger	= Logger.getLogger( StringUtil.class );

	private static final Pattern	pattern	= Pattern.compile( "\\{(.*?)\\}" );
	private static Matcher			matcher;

	/**
	 * Object转int
	 *
	 * @param obj
	 * @return
	 */
	public static int toInt(Object obj) {
		if (obj == null) {
			return 0;
		}
		try {
			return Integer.parseInt( obj.toString().trim() );
		} catch (Exception e) {
			logger.debug( "StringUtil.toInt", e );
		}
		return 0;
	}

	public static boolean isNullOrEmpty(String str) {
		return (isNull( str )) || (isEmpty( str ));
	}

	public static boolean isNotNullAndNotEmpty(String str) {
		return !isNullOrEmpty( str );
	}

	public static boolean isEmpty(String str) {
		return "".equals( str );
	}

	public static boolean isNull(String str) {
		if (str == null || "".equals( str )) {
			return true;
		}
		return false;
	}

	/**
	 * 格式化字符串 字符串中使用{key}表示占位符
	 * 
	 * @param sourStr
	 *            需要匹配的字符串
	 * @param param
	 *            参数集
	 * @return
	 */
	public static String stringFormat(String sourStr, Map<String, Object> param) {
		String tagerStr = sourStr;
		if (param == null)
			return tagerStr;
		matcher = pattern.matcher( tagerStr );
		while (matcher.find()) {
			String key = matcher.group();
			String keyclone = key.substring( 1, key.length() - 1 ).trim();
			Object value = param.get( keyclone );
			if (value != null)
				tagerStr = tagerStr.replace( key, value.toString() );
		}
		return tagerStr;
	}

	/**
	 * 格式化字符串 字符串中使用{key}表示占位符 利用反射 自动获取对象属性值 (必须有get方法)
	 * 
	 * @param sourStr
	 *            需要匹配的字符串
	 * @param param
	 *            参数集
	 * @return
	 */
	public static String stringFormat(String sourStr, Object obj) {
		String tagerStr = sourStr;
		matcher = pattern.matcher( tagerStr );
		if (obj == null)
			return tagerStr;

		PropertyDescriptor pd;
		Method getMethod;
		// 匹配{}中间的内容 包括括号
		while (matcher.find()) {
			String key = matcher.group();
			String keyclone = key.substring( 1, key.length() - 1 ).trim();
			try {
				pd = new PropertyDescriptor( keyclone, obj.getClass() );
				getMethod = pd.getReadMethod();// 获得get方法
				Object value = getMethod.invoke( obj );
				if (value != null)
					tagerStr = tagerStr.replace( key, value.toString() );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// Loggers.addException(e);
			}
		}
		return tagerStr;
	}

	/**
	 * 格式化字符串 (替换所有) 字符串中使用{key}表示占位符
	 * 
	 * @param sourStr
	 *            需要匹配的字符串
	 * @param param
	 *            参数集
	 * @return
	 */
	public static String stringFormatAll(String sourStr, Map<String, Object> param) {
		String tagerStr = sourStr;
		if (param == null)
			return tagerStr;
		matcher = pattern.matcher( tagerStr );
		while (matcher.find()) {
			String key = matcher.group();
			String keyclone = key.substring( 1, key.length() - 1 ).trim();
			Object value = param.get( keyclone );
			if (value != null)
				tagerStr = tagerStr.replace( key, value.toString() );
		}
		return tagerStr;
	}

	/**
	 * 格式化字符串 (替换所有) 字符串中使用{key}表示占位符
	 * 
	 * @param sourStr
	 *            需要匹配的字符串
	 * @param param
	 *            参数集
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String stringFormatAll(String sourStr, Object object) {
		String tagerStr = sourStr;
		Class param = object.getClass();
		if (param == null)
			return tagerStr;
		matcher = pattern.matcher( tagerStr );
		try {
			Field[] fields = param.getDeclaredFields();
			while (matcher.find()) {
				for (Field field : fields) {
					field.setAccessible( true );
					String key = matcher.group();
					String keyclone = key.substring( 1, key.length() - 1 ).trim();
					if (keyclone.equals( field.getName() )) {
						tagerStr = tagerStr.replace( key, field.get( object ).toString() );
					}
				}
			}
		} catch (Exception e) {
			logger.error( e.getMessage(), e );
		}
		return tagerStr;
	}

	// public static void main(String[] args) {
	// String sourStr = "您好!{myName}先生,恭喜您中了{money},请按时领取!";
	// MyTest myTest = new MyTest();
	// myTest.setMyName( "张三" );
	// myTest.setMoney( "1000万" );
	//
	// String result = stringFormatAll( sourStr, myTest );
	// System.out.println( "处理结果:" + result );
	// }
}
