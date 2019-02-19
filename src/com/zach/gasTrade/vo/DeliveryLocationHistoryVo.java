/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.vo;

import java.io.Serializable;

import javax.persistence.*;
import com.zach.gasTrade.common.PaginatedHelper;



public class DeliveryLocationHistoryVo extends PaginatedHelper implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//属性
	    /**
	     * id 
	     */ 
		private java.lang.String id;
	    /**
	     * deliveryUserId 
	     */ 
		private java.lang.String deliveryUserId;
	    /**
	     * longitude 
	     */ 
		private java.lang.String longitude;
	    /**
	     * latitude 
	     */ 
		private java.lang.String latitude;
	    /**
	     * location 
	     */ 
		private java.lang.String location;
	    /**
	     * createTime 
	     */ 
		private java.util.Date createTime;
	
	//getter setter方法
		
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
		
	public java.lang.String getDeliveryUserId() {
		return this.deliveryUserId;
	}
	
	public void setDeliveryUserId(java.lang.String value) {
		this.deliveryUserId = value;
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
	
		
	public java.lang.String getLocation() {
		return this.location;
	}
	
	public void setLocation(java.lang.String value) {
		this.location = value;
	}
	
		
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
		
}