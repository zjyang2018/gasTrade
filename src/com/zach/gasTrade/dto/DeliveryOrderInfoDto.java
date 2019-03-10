package com.zach.gasTrade.dto;

import com.zach.gasTrade.common.PaginatedHelper;

public class DeliveryOrderInfoDto extends PaginatedHelper implements java.io.Serializable {
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
	 * imagePath
	 */
	private java.lang.String imagePath;

	/**
	 * 客户姓名
	 */
	private java.lang.String customerName;
	/**
	 * 客户手机号
	 */
	private java.lang.String customerPhoneNumber;

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

}
