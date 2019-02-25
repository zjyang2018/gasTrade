/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.dto;

import com.zach.gasTrade.common.PaginatedHelper;



public class AdminUserDto extends PaginatedHelper implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//属性
	    /**
	     * id 
	     */ 
		private java.lang.String id;
	    /**
	     * 姓名 
	     */ 
		private java.lang.String name;
	    /**
	     * 性别:1-男,0-女 
	     */ 
		private java.lang.String sex;
	    /**
	     * 手机号码 
	     */ 
		private java.lang.String phoneNumber;
	    /**
	     * loginName 
	     */ 
		private java.lang.String loginName;
	    /**
	     * password 
	     */ 
		private java.lang.String password;
		/**
	     * initial password 
	     */ 
		private java.lang.String initialPassword;
		/**
	     * confirm password 
	     */ 
		private java.lang.String comfirmPassword;
	    /**
	     * 账号状态:10-正常,20-冻结 
	     */ 
		private java.lang.String accountStatus;
	    /**
	     * 在职状态:10-在职,20-离职 
	     */ 
		private java.lang.String workStatus;
	    /**
	     * 身份证号码 
	     */ 
		private java.lang.String idcardNo;
	    /**
	     * 联系地址 
	     */ 
		private java.lang.String address;
	    /**
	     * 备注 
	     */ 
		private java.lang.String remark;
	    /**
	     * createTime 
	     */ 
		private java.util.Date createTime;
	    /**
	     * updateTime 
	     */ 
		private java.util.Date updateTime;
	
	//getter setter方法
		
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
		
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
		
	public java.lang.String getSex() {
		return this.sex;
	}
	
	public void setSex(java.lang.String value) {
		this.sex = value;
	}
	
		
	public java.lang.String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public void setPhoneNumber(java.lang.String value) {
		this.phoneNumber = value;
	}
	
		
	public java.lang.String getLoginName() {
		return this.loginName;
	}
	
	public void setLoginName(java.lang.String value) {
		this.loginName = value;
	}
	
		
	public java.lang.String getPassword() {
		return this.password;
	}
	
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	
	
	public java.lang.String getInitialPassword() {
		return initialPassword;
	}

	public void setInitialPassword(java.lang.String initialPassword) {
		this.initialPassword = initialPassword;
	}

	public java.lang.String getComfirmPassword() {
		return comfirmPassword;
	}

	public void setComfirmPassword(java.lang.String comfirmPassword) {
		this.comfirmPassword = comfirmPassword;
	}

	public java.lang.String getAccountStatus() {
		return this.accountStatus;
	}
	
	public void setAccountStatus(java.lang.String value) {
		this.accountStatus = value;
	}
	
		
	public java.lang.String getWorkStatus() {
		return this.workStatus;
	}
	
	public void setWorkStatus(java.lang.String value) {
		this.workStatus = value;
	}
	
		
	public java.lang.String getIdcardNo() {
		return this.idcardNo;
	}
	
	public void setIdcardNo(java.lang.String value) {
		this.idcardNo = value;
	}
	
		
	public java.lang.String getAddress() {
		return this.address;
	}
	
	public void setAddress(java.lang.String value) {
		this.address = value;
	}
	
		
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
		
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
		
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
		
}