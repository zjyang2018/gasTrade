package com.common.filter.util;

/**
 * @Project common-utils
 * @Description: xss 清理工具方法
 * @Company youku
 * @Create 2015年10月14日下午9:18:50
 * @author zhoushaoyu
 * @version 1.0 Copyright (c) 2015 youku, All Rights Reserved.
 */

public class XssUtil {
	public static String cleanXSS(String value) {
		value = value.replaceAll( "<", "& lt;" ).replaceAll( ">", "& gt;" );
		value = value.replaceAll( "\\(", "& #40;" ).replaceAll( "\\)", "& #41;" );
		value = value.replaceAll( "'", "& #39;" );
		value = value.replaceAll( "eval\\((.*)\\)", "" );
		value = value.replaceAll( "[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"" );
		value = value.replaceAll( "script", "" );
		return value;
	}
}
