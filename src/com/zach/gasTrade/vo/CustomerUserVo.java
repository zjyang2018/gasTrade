/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.vo;

import java.io.Serializable;

import javax.persistence.*;
import com.zach.gasTrade.common.PaginatedHelper;



public class CustomerUserVo extends PaginatedHelper implements java.io.Serializable{
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
	     * 微信昵称 
	     */ 
		private java.lang.String wxName;
	    /**
	     * wxOpenId 
	     */ 
		private java.lang.String wxOpenId;
	    /**
	     * sex 
	     */ 
		private java.lang.String sex;
	    /**
	     * 手机号码 
	     */ 
		private java.lang.String phoneNumber;
	    /**
	     * 联系地址 
	     */ 
		private java.lang.String address;
	    /**
	     * 经度 
	     */ 
		private java.lang.String longitude;
	    /**
	     * 纬度 
	     */ 
		private java.lang.String latitude;
	    /**
	     * 渠道来源:10-微信公众号,20-客服录入,30-其它 
	     */ 
		private java.lang.String channel;
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
	
		
	public java.lang.String getWxName() {
		return this.wxName;
	}
	
	public void setWxName(java.lang.String value) {
		this.wxName = value;
	}
	
		
	public java.lang.String getWxOpenId() {
		return this.wxOpenId;
	}
	
	public void setWxOpenId(java.lang.String value) {
		this.wxOpenId = value;
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
	
		
	public java.lang.String getAddress() {
		return this.address;
	}
	
	public void setAddress(java.lang.String value) {
		this.address = value;
	}
	
		
	public java.lang.String getLongitude() {
		return this.longitude;
	}
	
	public void setLongitude(java.lang.String value) {
		this.longitude = value;
	}
	
		
	public java.lang.String getLatitude() {
		return this.latitude;
	}
	
	public void setLatitude(java.lang.String value) {
		this.latitude = value;
	}
	
		
	public java.lang.String getChannel() {
		return this.channel;
	}
	
	public void setChannel(java.lang.String value) {
		this.channel = value;
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