/**  
 * Project Name:xiniu-online-pay  
 * File Name:RulesFactory.java  
 * Package Name:com.xiniu.online.filter  
 * Date:2017年1月9日下午2:14:42  
 * Copyright (c) 2017, 汇联基金 All Rights Reserved.  
 *  
*/

package com.common.filter.factory;

import com.common.filter.rule.impl.SqlRules;

/**
 * ClassName:RulesFactory <br/>
 * Description: TODO 添加描述. <br/>
 * Date: 2017年1月9日 下午2:14:42 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
public class RulesFactory {

	public static SqlRules createSqlRules() {
		return new SqlRules();
	}

}
