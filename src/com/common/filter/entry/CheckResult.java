/**  
 * Project Name:xiniu-online-pay  
 * File Name:CheckRuleResult.java  
 * Package Name:com.xiniu.online.filter  
 * Date:2017年1月9日下午2:05:55  
 * Copyright (c) 2017, 汇联基金 All Rights Reserved.  
 *  
*/

package com.common.filter.entry;

/**
 * ClassName:CheckRuleResult <br/>
 * Description: TODO 添加描述. <br/>
 * Date: 2017年1月9日 下午2:05:55 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
public class CheckResult {
	private boolean	checkPass;
	private String	massage;

	public boolean isCheckPass() {
		return checkPass;
	}

	public void setCheckPass(boolean checkPass) {
		this.checkPass = checkPass;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

}
