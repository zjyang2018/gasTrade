/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import com.zach.gasTrade.common.PaginatedHelper;



public class OrderFinanceStatisticsVo extends PaginatedHelper implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//属性
	    /**
	     * id 
	     */ 
		private java.lang.String id;
	    /**
	     * 统计日期 
	     */ 
		private java.lang.String date;
	    /**
	     * 下单者数量 
	     */ 
		private java.lang.Integer buyerCount;
	    /**
	     * 订单数量 
	     */ 
		private java.lang.Integer orderCount;
	    /**
	     * 支付订单数量 
	     */ 
		private java.lang.Integer payOrderCount;
	    /**
	     * 派送订单数量 
	     */ 
		private java.lang.Integer deliveryOrderCount;
	    /**
	     * 客单价(均价) 
	     */ 
		private BigDecimal avgAmount;
	    /**
	     * 订单总金额 
	     */ 
		private BigDecimal orderAmount;
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
	
		
	public java.lang.String getDate() {
		return this.date;
	}
	
	public void setDate(java.lang.String value) {
		this.date = value;
	}
	
		
	public java.lang.Integer getBuyerCount() {
		return this.buyerCount;
	}
	
	public void setBuyerCount(java.lang.Integer value) {
		this.buyerCount = value;
	}
	
		
	public java.lang.Integer getOrderCount() {
		return this.orderCount;
	}
	
	public void setOrderCount(java.lang.Integer value) {
		this.orderCount = value;
	}
	
		
	public java.lang.Integer getPayOrderCount() {
		return this.payOrderCount;
	}
	
	public void setPayOrderCount(java.lang.Integer value) {
		this.payOrderCount = value;
	}
	
		
	public java.lang.Integer getDeliveryOrderCount() {
		return this.deliveryOrderCount;
	}
	
	public void setDeliveryOrderCount(java.lang.Integer value) {
		this.deliveryOrderCount = value;
	}
	
		
	public BigDecimal getAvgAmount() {
		return this.avgAmount;
	}
	
	public void setAvgAmount(BigDecimal value) {
		this.avgAmount = value;
	}
	
		
	public BigDecimal getOrderAmount() {
		return this.orderAmount;
	}
	
	public void setOrderAmount(BigDecimal value) {
		this.orderAmount = value;
	}
	
		
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
		
}