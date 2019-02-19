package com.common.filter.wrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;

import com.common.filter.util.XssUtil;

/**
 * 
 * @Project ewallet-web
 * @Description:
 * @Company youku
 * @Create 2015年2月2日下午2:43:28
 * @author tengwenqing
 * @version 1.0 Copyright (c) 2015 youku, All Rights Reserved.
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private List<String> excludeParamNames;

	public XssHttpServletRequestWrapper(HttpServletRequest servletRequest, List<String> excludeParamNames) {
		super( servletRequest );
		this.excludeParamNames = excludeParamNames;
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues( parameter );
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[ i ] = XssUtil.cleanXSS( values[ i ] );
		}
		return encodedValues;
	}

	public String getParameter(String parameter) {
		String value = super.getParameter( parameter );
		if (excludeParamNames != null && excludeParamNames.size() > 0) {
			if (excludeParamNames.contains( parameter )) {
				return value;
			}
		}

		// System.out.println("====="+value);
		if (StringUtils.isNotEmpty( value )) {
			if (value.indexOf( "IMG" ) >= 0 || value.indexOf( "img" ) >= 0 || value.indexOf( "HREF" ) >= 0 || value.indexOf( "href" ) >= 0
					|| value.indexOf( "JAVASCRIPT" ) >= 0 || value.indexOf( "javascript" ) >= 0 || value.indexOf( "SCRIPT" ) >= 0
					|| value.indexOf( "script" ) >= 0 || value.indexOf( "ALERT" ) >= 0 || value.indexOf( "alert" ) >= 0
			// || value.indexOf("HTTP") >= 0
			// || value.indexOf("http") >= 0
			) {
				value = "";
			}
		}
		if (value == null) {
			return null;
		}
		// System.out.println("-----"+cleanXSS(value));
		return XssUtil.cleanXSS( value );
	}

	public String getHeader(String name) {
		String value = super.getHeader( name );
		if (value == null)
			return null;
		return XssUtil.cleanXSS( value );
	}

}
