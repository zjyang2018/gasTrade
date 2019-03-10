package com.zach.gasTrade.dto;

import java.math.BigDecimal;

import com.zach.gasTrade.common.PaginatedHelper;

public class DeliveryDetailDto extends PaginatedHelper implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// 属性
	/**
	 * orderId
	 */
	private java.lang.String orderId;
	/**
	 * productName
	 */
	private java.lang.String productName;
	/**
	 * 产品金额
	 */
	private BigDecimal amount;
	/**
	 * 支付时间
	 */
	private java.util.Date payTime;
	/**
	 * 接单派送时间
	 */
	private java.util.Date deliveryOrderTime;
	/**
	 * 派送员名称
	 */
	private java.lang.String deliveryName;
	/**
	 * 手机号码
	 */
	private java.lang.String deliveryPhoneNumber;
	/**
	 * 商家地址
	 */
	private java.lang.String productAddress;
	/**
	 * 客户地址
	 */
	private java.lang.String customerAddress;
	
	// getter setter方法
	public java.lang.String getOrderId() {
		return orderId;
	}
	public void setOrderId(java.lang.String orderId) {
		this.orderId = orderId;
	}
	public java.lang.String getProductName() {
		return productName;
	}
	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	public java.lang.String getProductAddress() {
		return productAddress;
	}
	public void setProductAddress(java.lang.String productAddress) {
		this.productAddress = productAddress;
	}
	public java.lang.String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(java.lang.String customerAddress) {
		this.customerAddress = customerAddress;
	}
		
		
}
