package com.zach.gasTrade.dto;

import java.math.BigDecimal;

import com.zach.gasTrade.common.PaginatedHelper;

public class CustomerOrderGenerateInfoDto extends PaginatedHelper implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// 属性
	/**
	 * orderId
	 */
	private java.lang.String orderId;
	/**
	 * imagePath
	 */
	private java.lang.String imagePath;
	/**
	 * 产品金额
	 */
	private BigDecimal productAmount;
	/**
	 * 客户地址
	 */
	private java.lang.String address;
	/**
	 * 手机号码
	 */
	private java.lang.String customerPhoneNumber;
	/**
	 * 备注
	 */
	private java.lang.String remark;
	// getter setter方法
	public java.lang.String getOrderId() {
		return orderId;
	}
	public void setOrderId(java.lang.String orderId) {
		this.orderId = orderId;
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
	public java.lang.String getAddress() {
		return address;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	public java.lang.String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}
	public void setCustomerPhoneNumber(java.lang.String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(String string) {
		this.remark = string;
	}
	
	
	
}
