package com.zach.gasTrade.dto;

import java.math.BigDecimal;

import com.zach.gasTrade.common.PaginatedHelper;

public class OrderListDto extends PaginatedHelper implements java.io.Serializable {
private static final long serialVersionUID = 5454155825314635342L;
	
	//属性
	    /**
	     * orderId 
	     */ 
		private java.lang.String orderId;
	    /**
	     * 订单金额 
	     */ 
		private BigDecimal amount;
		/**
	     * 订单金额汇总 
	     */ 
		private BigDecimal totalAmount;
	    /**
	     * 支付状态:10-未支付,20-已支付 
	     */ 
		private java.lang.String payStatus;
		/**
	     * 客户姓名 
	     */ 
		private java.lang.String customerName;
		/**
	     * 客户手机号 
	     */ 
		private java.lang.String customerPhoneNumber;
	    /**
	     * 分配状态:10-未分配，20-已分配 
	     */ 
		private java.lang.String allotStatus;
		/**
	     * 派送员名称 
	     */ 
		private java.lang.String deliveryName;
		/**
	     * 派送员手机号 
	     */ 
		private java.lang.String deliveryPhoneNumber;
	    /**
	     * 订单状态:10-未支付,20-已支付,30-已分配,40-已接单,50-派送中,60-派送完成 
	     */ 
		private java.lang.String orderStatus;
		/**
	     * 支付时间 
	     */ 
		private java.util.Date payTime;
		/**
	     * 接单派送时间 
	     */ 
		private java.util.Date deliveryOrderTime;
		
		//getter setter方法
		
		public java.lang.String getOrderId() {
			return orderId;
		}
		public void setOrderId(java.lang.String orderId) {
			this.orderId = orderId;
		}
		public BigDecimal getAmount() {
			return amount;
		}
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
		
		public BigDecimal getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(BigDecimal totalAmount) {
			this.totalAmount = totalAmount;
		}
		public java.lang.String getPayStatus() {
			return payStatus;
		}
		public void setPayStatus(java.lang.String payStatus) {
			this.payStatus = payStatus;
		}
		public java.lang.String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(java.lang.String customerName) {
			this.customerName = customerName;
		}
		public java.lang.String getCustomerPhoneNumber() {
			return customerPhoneNumber;
		}
		public void setCustomerPhoneNumber(java.lang.String customerPhoneNumber) {
			this.customerPhoneNumber = customerPhoneNumber;
		}
		public java.lang.String getAllotStatus() {
			return allotStatus;
		}
		public void setAllotStatus(java.lang.String allotStatus) {
			this.allotStatus = allotStatus;
		}
		public java.lang.String getDeliveryName() {
			return deliveryName;
		}
		public void setDeliveryName(java.lang.String deliveryName) {
			this.deliveryName = deliveryName;
		}
		public java.lang.String getDeliveryPhoneNumber() {
			return deliveryPhoneNumber;
		}
		public void setDeliveryPhoneNumber(java.lang.String deliveryPhoneNumber) {
			this.deliveryPhoneNumber = deliveryPhoneNumber;
		}
		public java.lang.String getOrderStatus() {
			return orderStatus;
		}
		public void setOrderStatus(java.lang.String orderStatus) {
			this.orderStatus = orderStatus;
		}
		public java.util.Date getPayTime() {
			return payTime;
		}
		public void setPayTime(java.util.Date payTime) {
			this.payTime = payTime;
		}
		public java.util.Date getDeliveryOrderTime() {
			return deliveryOrderTime;
		}
		public void setDeliveryOrderTime(java.util.Date deliveryOrderTime) {
			this.deliveryOrderTime = deliveryOrderTime;
		}
		
	    
}
