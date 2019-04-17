package com.zach.gasTrade.dto;

import com.zach.gasTrade.common.PaginatedHelper;

public class DeliveryMonitorDto extends PaginatedHelper implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// 属性
	/**
	 * orderId
	 */
	private java.lang.String orderId;
	/**
	 * 客户姓名
	 */
	private java.lang.String customerName;
	/**
	 * 客户手机号
	 */
	private java.lang.String customerPhoneNumber;
	/**
	 * 派送员名称
	 */
	private java.lang.String deliveryName;
	/**
	 * 派送员手机号
	 */
	private java.lang.String deliveryPhoneNumber;
	/**
	 * 下单时间
	 */
	private java.util.Date deliveryOrderTime;

	/**
	 * 订单备注
	 */
	private String remark;

	// getter setter方法

	public java.lang.String getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.String orderId) {
		this.orderId = orderId;
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

	public java.util.Date getDeliveryOrderTime() {
		return deliveryOrderTime;
	}

	public void setDeliveryOrderTime(java.util.Date deliveryOrderTime) {
		this.deliveryOrderTime = deliveryOrderTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
