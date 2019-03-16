package com.zach.gasTrade.dto;

import java.math.BigDecimal;

import com.zach.gasTrade.common.PaginatedHelper;

public class CustomerOrderInfoDto extends PaginatedHelper implements java.io.Serializable {
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
	 * 产品描述
	 */
	private java.lang.String productDesc;

	/**
	 * imagePath
	 */
	private java.lang.String imagePath;
	/**
	 * 产品金额
	 */
	private BigDecimal productAmount;
	/**
	 * 派送员名称
	 */
	private java.lang.String deliveryName;
	/**
	 * 手机号码
	 */
	private java.lang.String deliveryPhoneNumber;
	/**
	 * 派送完成时间
	 */
	private java.util.Date deliveryCompleteTime;
	/**
	 * 客户姓名
	 */
	private java.lang.String customerName;
	/**
	 * 备注
	 */
	private java.lang.String remark;

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

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

	public java.lang.String getImagePath() {
		return imagePath;
	}

	public void setImagePath(java.lang.String imagePath) {
		this.imagePath = imagePath;
	}

	public BigDecimal getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
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

	public java.util.Date getDeliveryCompleteTime() {
		return deliveryCompleteTime;
	}

	public void setDeliveryCompleteTime(java.util.Date deliveryCompleteTime) {
		this.deliveryCompleteTime = deliveryCompleteTime;
	}

	public java.lang.String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(java.lang.String customerName) {
		this.customerName = customerName;
	}

	public java.lang.String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(java.lang.String productDesc) {
		this.productDesc = productDesc;
	}

}
