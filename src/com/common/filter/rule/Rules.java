/**  
 * Project Name:xiniu-online-pay  
 * File Name:Rules.java  
 * Package Name:com.xiniu.online.filter  
 * Date:2017年1月9日下午2:04:03  
 * Copyright (c) 2017, 汇联基金 All Rights Reserved.  
 *  
*/

package com.common.filter.rule;

import javax.servlet.ServletRequest;

import com.common.filter.entry.CheckResult;

/**
 * ClassName:Rules <br/>
 * Description: TODO 添加描述. <br/>
 * Date: 2017年1月9日 下午2:04:03 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
public interface Rules {
	// 规则校验
	CheckResult check(ServletRequest request);

}
