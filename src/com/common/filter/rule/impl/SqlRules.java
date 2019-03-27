/**  
 * Project Name:xiniu-online-pay  
 * File Name:SqlRules.java  
 * Package Name:com.xiniu.online.filter  
 * Date:2017年1月9日下午2:10:20  
 * Copyright (c) 2017, 汇联基金 All Rights Reserved.  
 *  
*/

package com.common.filter.rule.impl;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;

import com.common.filter.entry.CheckResult;
import com.common.filter.rule.Rules;

/**
 * ClassName:SqlRules <br/>
 * Description: sql防注入实现. <br/>
 * Date: 2017年1月9日 下午2:10:20 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
public class SqlRules implements Rules {

	@Override
	public CheckResult check(ServletRequest request) {
		CheckResult result = new CheckResult();
		result.setCheckPass(true);
		StringBuffer message = new StringBuffer();
		Map<String, String[]> params = request.getParameterMap();
		for (Entry<String, String[]> entry : params.entrySet()) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			for (String value : values) {
				if (sqlValidate(value)) {
					result.setCheckPass(false);
					message.append("参数：" + key + "包含非法字符");
				}
			}
		}
		result.setMassage(message.toString());
		return result;

	}

	protected static boolean sqlValidate(String str) {
		str = str.toLowerCase();// 统一转为小写
		String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|"
				+ "char|declare|sitename|net user|xp_cmdshell|;|or|+|,|like'|and|exec|execute|insert|create|drop|"
				+ "table|from|grant|use|group_concat|column_name|"
				+ "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|"
				+ "chr|mid|master|truncate|char|declare|or|;|--|+|,|like|//|/|%|#";// 过滤掉的sql关键字，可以手动添加
		String[] badStrs = badStr.split("\\|");
		for (int i = 0; i < badStrs.length; i++) {
			if (str.indexOf(badStrs[i]) >= 0) {
				return true;
			}
		}
		return false;
	}

}
