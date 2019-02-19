package com.common.filter.wrapper;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @Project ewallet-web
 * @Description: crlf注入进行过滤
 * @Company youku
 * @Create 2014-11-21上午11:09:53
 * @author guochao
 * @version 1.0 Copyright (c) 2014 youku, All Rights Reserved.
 */
public class CrlfFilterResponseWrapper extends HttpServletResponseWrapper {
	/**
	 * http header name 不允许出现的字符
	 */
	private static final char[] headerName_tspecials = new char[] { '\n', '\r' };
	static {
		Arrays.sort( headerName_tspecials );
	}

	public CrlfFilterResponseWrapper(HttpServletResponse response) throws IOException {
		super( response );
	}

	public void addHeader(String name, String value) {
		super.addHeader( filterHeaderName( name ), filterHeaderValue( value ) );
	}

	public void sendError(int sc, String msg) throws IOException {
		super.sendError( sc, filterHeaderValue( msg ) );
	}

	public void sendRedirect(String location) throws IOException {
		super.sendRedirect( filterHeaderValue( location ) );
	}

	public void setHeader(String name, String value) {
		super.setHeader( filterHeaderName( name ), filterHeaderValue( value ) );
	}

	@SuppressWarnings("deprecation")
	public void setStatus(int sc, String sm) {
		super.setStatus( sc, filterHeaderValue( sm ) );
	}

	public void addDateHeader(String name, long date) {
		super.addDateHeader( filterHeaderName( name ), date );
	}

	public void addIntHeader(String name, int value) {
		super.addIntHeader( filterHeaderName( name ), value );
	}

	public void setDateHeader(String name, long date) {
		super.setDateHeader( filterHeaderName( name ), date );
	}

	public void setIntHeader(String name, int value) {
		super.setIntHeader( filterHeaderName( name ), value );
	}

	public void setContentType(String contentType) {
		super.setContentType( filterHeaderValue( contentType ) );
	}

	/**
	 * 
	 * 方法用途: 过滤头信息名字中的非法字符，避免CRLF注入攻击 Many HTTP/1.1 header field values consist
	 * of words separated by LWS<br/>
	 * or special characters. These special characters MUST be in a quoted<br/>
	 * string to be used within a parameter value.<br/>
	 * 
	 * token = 1*<any CHAR except CTLs or tspecials><br/>
	 * 
	 * tspecials = "(" | ")" | "<" | ">" | "@"<br/>
	 * | "," | ";" | ":" | "\" | <"><br/>
	 * | "/" | "[" | "]" | "?" | "="<br/>
	 * | "{" | "}" | SP | HT <br/>
	 * CTL = <any US-ASCII control character<br/>
	 * (octets 0 - 31) and DEL (127)><br/>
	 * SP = <US-ASCII SP, space (32)><br/>
	 * HT = <US-ASCII HT, horizontal-tab (9)><br>
	 * 实现步骤: <br>
	 * 
	 * @param name
	 * @return
	 */
	private static String filterHeaderName(String name) {

		if (name == null || name.length() < 1) {
			return "null";
		}
		StringBuilder sb = new StringBuilder( name.length() );
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt( i );
			if (c > 32 && c < 127 && Arrays.binarySearch( headerName_tspecials, c ) < 0) {
				sb.append( c );
			}
		}
		return sb.toString();
	}

	/**
	 * 方法用途: 过滤头信息值中的非法字符，避免CRLF注入攻击 field-value = *( field-content | LWS )<br/>
	 * field-content = <the OCTETs making up the field-value<br/>
	 * and consisting of either *TEXT or combinations<br/>
	 * of token, tspecials, and quoted-string><br>
	 * 实现步骤: <br>
	 * 
	 * @param value
	 * @return
	 */
	private static String filterHeaderValue(String value) {

		if (value == null || value.length() < 1) {
			return "null";
		}
		StringBuilder sb = new StringBuilder( value.length() );
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt( i );
			if (c >= 32 && c < 127) {
				sb.append( c );
			}
		}
		return sb.toString();
	}
}
