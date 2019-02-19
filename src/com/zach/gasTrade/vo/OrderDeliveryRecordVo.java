/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.vo;

import java.io.Serializable;

import javax.persistence.*;
import com.zach.gasTrade.common.PaginatedHelper;



public class OrderDeliveryRecordVo extends PaginatedHelper implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//属性
	    /**
	     * id 
	     */ 
		private java.lang.String id;
	    /**
	     * orderId 
	     */ 
		private java.lang.String orderId;
	    /**
	     * 分配时间 
	     */ 
		private java.util.Date allotTime;
	    /**
	     * 分配订单描述 
	     */ 
		private java.lang.String allotDesc;
	    /**
	     * 接单时间 
	     */ 
		private java.util.Date acceptTime;
	    /**
	     * 接单描述 
	     */ 
		private java.lang.String acceptDesc;
	    /**
	     * 派送时间 
	     */ 
		private java.util.Date deliveryTime;
	    /**
	     * 派送订单描述 
	     */ 
		private java.lang.String deliveryDesc;
	    /**
	     * 完成时间 
	     */ 
		private java.util.Date completeTime;
	    /**
	     * 订单完成描述 
	     */ 
		private java.lang.String completeDesc;
	    /**
	     * 起始经纬度 
	     */ 
		private java.lang.String startLocation;
	    /**
	     * 终点经纬度 
	     */ 
		private java.lang.String endLocation;
	    /**
	     * 移动中的经纬度 
	     */ 
		private java.lang.String moveLocation;
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
	
		
	public java.lang.String getOrderId() {
		return this.orderId;
	}
	
	public void setOrderId(java.lang.String value) {
		this.orderId = value;
	}
	
		
	public java.util.Date getAllotTime() {
		return this.allotTime;
	}
	
	public void setAllotTime(java.util.Date value) {
		this.allotTime = value;
	}
	
		
	public java.lang.String getAllotDesc() {
		return this.allotDesc;
	}
	
	public void setAllotDesc(java.lang.String value) {
		this.allotDesc = value;
	}
	
		
	public java.util.Date getAcceptTime() {
		return this.acceptTime;
	}
	
	public void setAcceptTime(java.util.Date value) {
		this.acceptTime = value;
	}
	
		
	public java.lang.String getAcceptDesc() {
		return this.acceptDesc;
	}
	
	public void setAcceptDesc(java.lang.String value) {
		this.acceptDesc = value;
	}
	
		
	public java.util.Date getDeliveryTime() {
		return this.deliveryTime;
	}
	
	public void setDeliveryTime(java.util.Date value) {
		this.deliveryTime = value;
	}
	
		
	public java.lang.String getDeliveryDesc() {
		return this.deliveryDesc;
	}
	
	public void setDeliveryDesc(java.lang.String value) {
		this.deliveryDesc = value;
	}
	
		
	public java.util.Date getCompleteTime() {
		return this.completeTime;
	}
	
	public void setCompleteTime(java.util.Date value) {
		this.completeTime = value;
	}
	
		
	public java.lang.String getCompleteDesc() {
		return this.completeDesc;
	}
	
	public void setCompleteDesc(java.lang.String value) {
		this.completeDesc = value;
	}
	
		
	public java.lang.String getStartLocation() {
		return this.startLocation;
	}
	
	public void setStartLocation(java.lang.String value) {
		this.startLocation = value;
	}
	
		
	public java.lang.String getEndLocation() {
		return this.endLocation;
	}
	
	public void setEndLocation(java.lang.String value) {
		this.endLocation = value;
	}
	
		
	public java.lang.String getMoveLocation() {
		return this.moveLocation;
	}
	
	public void setMoveLocation(java.lang.String value) {
		this.moveLocation = value;
	}
	
		
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
		
}