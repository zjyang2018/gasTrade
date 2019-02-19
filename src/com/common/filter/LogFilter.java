package com.common.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.common.constant.CommonConstant;
import com.common.filter.util.HttpUtil;
import com.common.filter.wrapper.XiNiuHttpServletResponseWrapper;

/**
 * 
 * @Description: * 记录access log
 * 
 *               e.g.127.0.0.1 - - [2014/11/23:19:20:22 +0800]
 *               "GET /charge/chargeView.htm?operation=main HTTP/1.1" 200 -
 *               "http://test.xiniu.com/charge/chargeView.htm?operation=chargeConfirm"
 *               "Mozilla/5.0 (Windows NT 6.1; rv:16.0) Gecko/20100101 Firefox/16.0"
 *               26551 adbAFVKYEaPjfaW3R-gSt {http--80-6$9782817} -127.0.0.1：
 *               访问客户端ip -[2014/11/23:19:20:22 +0800]： 请求到达时间 -
 *               "GET /charge/chargeView.htm? HTTP/1.1"： 请求方式 请求url+参数 协议 -200：
 *               请求返还状态码 -http://test.xiniu.com/charge/chargeView.htm?operation
 *               =chargeConfirm： referer -
 *               "Mozilla/5.0 (Windows NT 6.1; rv:16.0) Gecko/20100101 Firefox/16.0"
 *               ： user-agent -26551 ： 请求处理时间 (单位：毫秒) -adbAFVKYEaPjfaW3R-gSt：
 *               当前请求 session id -{http--80-6$9782817}： 当前线程id
 * @Company
 * @Create 2017-01-10上午10:29:12
 * @author
 * @version 1.0
 */
public class LogFilter extends HttpServlet implements Filter {

	private static final long	serialVersionUID		= 8440219491928138602L;

	private static Logger		accessLogger			= Logger.getLogger( "com.xiniu.online.front.AccessLog" );

	private static Logger		logger					= Logger.getLogger( "LogFilter" );

	private Logger				log						= Logger.getLogger( getClass() );

	private static String		encryptParamNamesStr	= "pwd,paypwd,password,paypassword,repwd,repaypwd,repassword,repaypassword,newpaypassword,renewpaypassword,paypasswordinput";

	private static Set<String>	encryptParamNames		= new HashSet<String>();

	public void init(FilterConfig filterConfig) throws ServletException {
		log.info( "系统启动开始" );
		if (encryptParamNames.size() == 0) {
			String[] strs = encryptParamNamesStr.split( "," );
			Collections.addAll( encryptParamNames, strs );
		}
	}

	public void destroy() {
		super.destroy();
		log.info( "系统shutdown,destroy" );
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		Date begin = new Date();
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String accessLoggerStr = "";
		int statusCode = 500;
		HttpSession session = request.getSession();
		String id = session.getId();
		try {
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			XiNiuHttpServletResponseWrapper responseWrapper = new XiNiuHttpServletResponseWrapper( response );
			chain.doFilter( servletRequest, responseWrapper );
			statusCode = responseWrapper.getStatusCode();
		} catch (Throwable e) {
			logger.fatal( "access log filter throw exception:", e );
			throw new ServletException( "access log filter throw exception:", e );
		} finally {
			accessLoggerStr = getAccessLog( request, statusCode, begin, id );
			accessLogger.info( accessLoggerStr );
		}

	}

	@SuppressWarnings("rawtypes")
	private static String getAccessLog(HttpServletRequest request, int stateCode, Date begin, String id) {
		StringBuilder sb = new StringBuilder();
		try {
			// user ip
			sb.append( HttpUtil.getRemoteAddr( request ) ).append( " - - " );

			// request time
			SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd:HH:mm:ss" );

			String time = format.format( begin );
			sb.append( "[" ).append( time ).append( " +0800] " );

			// request method
			String method = request.getMethod();
			sb.append( "\"" ).append( method ).append( " " );

			// request url
			/*
			 * sb.append("http://"); sb.append(request.getHeader("Host"));
			 */
			sb.append( request.getRequestURI() );

			// request params
			if ("GET".equals( method )) {
				sb.append( request.getQueryString() == null ? "" : "?" + request.getQueryString() ).append( " " );
			} else {
				sb.append( "?" );
				Enumeration enumeration = request.getParameterNames();

				while (enumeration.hasMoreElements()) {
					String paramName = (String) enumeration.nextElement();
					String value = request.getParameter( paramName );
					if (encryptParamNames.contains( paramName.toLowerCase() )) {
						sb.append( paramName ).append( "=" ).append( "XXX" ).append( "&" );

					} else {
						try {
							value = URLEncoder.encode( value, CommonConstant.ENCODING_UTF8 );
						} catch (UnsupportedEncodingException e) {
							logger.fatal( e );
						}
						sb.append( paramName ).append( "=" ).append( value ).append( "&" );
					}
				}
				sb.deleteCharAt( sb.length() - 1 ).append( " " );
			}

			// protocol
			sb.append( request.getProtocol() ).append( "\" " );

			// state code
			sb.append( stateCode ).append( " - " );

			// referer
			String referer = request.getHeader( "Referer" );
			sb.append( "\"" ).append( StringUtils.isBlank( referer ) ? "-" : referer ).append( "\" " );

			// user agent
			String userAgent = request.getHeader( "User-Agent" );
			sb.append( "\"" ).append( StringUtils.isBlank( userAgent ) ? "-" : userAgent ).append( "\" " );

			// 响应时间
			sb.append( String.valueOf( System.currentTimeMillis() - begin.getTime() ) ).append( " " );

			// 打印ytid或者uid
			// String ytId = parseYtid(request);
			// sb.append("\"").append(StringUtils.isBlank(ytId) ? "-" :
			// ytId).append("\" ");
			// session id
			sb.append( "\"" ).append( id ).append( "\" " );

		} catch (Exception e) {
			logger.fatal( "get access log string Exception:", e );
		}
		return sb.toString();
	}

}
