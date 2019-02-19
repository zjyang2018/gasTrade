package com.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.common.filter.wrapper.CrlfFilterResponseWrapper;

public class CrlfFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletResponse response = new CrlfFilterResponseWrapper( (HttpServletResponse) res );
		chain.doFilter( req, response );
	}

	public void init(FilterConfig config) throws ServletException {

	}

}
