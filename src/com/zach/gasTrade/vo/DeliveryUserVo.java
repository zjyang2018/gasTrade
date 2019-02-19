/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.vo;

import java.io.Serializable;

import javax.persistence.*;
import com.zach.gasTrade.common.PaginatedHelper;



public class DeliveryUserVo extends PaginatedHelper implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//属性
	    /**
	     * id 
	     */ 
		private java.lang.String id;
	    /**
	     * 派送员名称 
	     */ 
		private java.lang.String name;
	    /**
	     * 手机号码 
	     */ 
		private java.lang.String phoneNumber;
	    /**
	     * 派送员登录名称 
	     */ 
		private java.lang.String loginName;
	    /**
	     * 登录密码 
	     */ 
		private java.lang.String password;
	    /**
	     * 移动定位设备编号 
	     */ 
		private java.lang.String equipNo;
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
	     * 账号状态:10-正常，20-冻结 
	     */ 
		private java.lang.String accountStatus;
	    /**
	     * 工作状态:10-派送中,20-空闲,30-请假 
	     */ 
		private java.lang.String workStatus;
	    /**
	     * 冻结原因 
	     */ 
		private java.lang.String frozenReason;
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
	
		
	public java.lang.String getEquipNo() {
		return this.equipNo;
	}
	
	public void setEquipNo(java.lang.String value) {
		this.equipNo = value;
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
	
		
	public java.lang.String getFrozenReason() {
		return this.frozenReason;
	}
	
	public void setFrozenReason(java.lang.String value) {
		this.frozenReason = value;
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