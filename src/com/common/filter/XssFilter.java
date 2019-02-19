package com.common.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.common.filter.wrapper.XssHttpServletRequestWrapper;

/**
 * 
 * @Project ewallet-web
 * @Description: 跨站脚本攻击过滤
 * @Company youku
 * @Create 2015年2月2日下午2:43:05
 * @author tengwenqing
 * @version 1.0 Copyright (c) 2015 youku, All Rights Reserved.
 */
public class XssFilter implements Filter {

	private List<String>	excludeParamNameList	= new ArrayList<String>();

	private FilterConfig	filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		String excludeParamNames = filterConfig.getInitParameter( "excludeParamNames" );
		if (excludeParamNames != null) {
			String[] params = excludeParamNames.split( "," );
			for (int i = 0; i < params.length; i++) {
				excludeParamNameList.add( params[ i ].trim() );
			}
		}
	}

	public void destroy() {
		if (this.filterConfig != null) {
			this.filterConfig = null;
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter( new XssHttpServletRequestWrapper( (HttpServletRequest) request, excludeParamNameList ), response );
	}
}