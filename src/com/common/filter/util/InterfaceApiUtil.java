/**  
 * Project Name:xiniu-online-front  
 * File Name:InterfaceApiUtil.java  
 * Package Name:com.xiniu.online.util  
 * Date:2017年1月9日下午4:45:27  
 * Copyright (c) 2017, 汇联基金 All Rights Reserved.  
 *  
*/

package com.common.filter.util;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import com.common.http.HttpResult;
import com.common.http.HttpTransfer;

/**
 * ClassName:InterfaceApiUtil <br/>
 * Description: 接口请求工具类 <br/>
 * Date: 2017年1月9日 下午4:45:27 <br/>
 * 
 * @author tony
 * @version
 * @since JDK 1.7
 * @see
 */
public class InterfaceApiUtil {

	private static ResourceBundle bundle = ResourceBundle.getBundle( "api.properties" );

	private static String getPropertyValue(String string) {
		return bundle.getString( string );
	}

	/**
	 * getTradList:(获取交易记录接口). <br/>
	 * (主要用于查询基金交易的记录).<br/>
	 * 
	 * @author tony
	 * @param map
	 * @return
	 * @since JDK 1.7
	 */
	public static HttpResult getTradList(Map<String, String> data, HttpTransfer httpTransfer) {
		HttpResult result = null;
		try {
			result = httpTransfer.doPost( String.format( getPropertyValue( "jrhttp" ), getPropertyValue( "core" ) ), data );
		} catch (IOException ex) {
			throw new RuntimeException( "连接接口异常" );
		}
		return result;
	}

	/**
	 * getTradList: 查询支付记录<br/>
	 * (主要用于查询支付交易的记录).<br/>
	 * 
	 * @author tony
	 * @param map
	 * @return
	 * @since JDK 1.7
	 */
	public static HttpResult getPayList(Map<String, String> data, HttpTransfer httpTransfer) {
		HttpResult result = null;
		try {
			result = httpTransfer.doPost( String.format( getPropertyValue( "jrhttp" ), getPropertyValue( "pay" ) ), data );
		} catch (IOException ex) {
			throw new RuntimeException( "连接接口异常" );
		}
		return result;
	}

}
