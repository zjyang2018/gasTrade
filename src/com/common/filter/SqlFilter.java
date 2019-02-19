/**  
 * Project Name:xiniu-online-pay  
 * File Name:HttpLogFilter.java  
 * Package Name:com.xiniu.online.filter  
 * Date:2017年1月9日上午10:51:05  
 * Copyright (c) 2017, 汇联基金 All Rights Reserved.  
 *  
*/

package com.common.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.common.filter.entry.CheckResult;
import com.common.filter.factory.RulesFactory;
import com.common.filter.rule.Rules;

/**
 * ClassName:HttpLogFilter <br/>
 * Description: TODO 添加描述. <br/>
 * Date: 2017年1月9日 上午10:51:05 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
public class SqlFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Rules rules = RulesFactory.createSqlRules();
		CheckResult result = rules.check( request );
		if (result.isCheckPass()) {
			chain.doFilter( request, response );
		} else {
			PrintWriter writer = response.getWriter();
			writer.print( result.getMassage() );
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}
