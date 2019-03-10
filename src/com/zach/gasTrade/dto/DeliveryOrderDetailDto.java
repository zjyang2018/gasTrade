package com.zach.gasTrade.dto;

import com.zach.gasTrade.common.PaginatedHelper;

public class DeliveryOrderDetailDto extends PaginatedHelper implements java.io.Serializable {
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
     * 库存数量 
     */ 
	private java.lang.Integer stockQty;
	/**
	 * 客户姓名 
     */ 
	private java.lang.String customerName;
	/**
	 * 客户电话 
     */ 
	private java.lang.String customerPhoneNumber;
	/**
	 * 客户地址
	 */
	private java.lang.String customerAddress;
	/**
	 * 商家地址
	 */
	private java.lang.String productAddress;
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
	public java.lang.String getProductName() {
		return productName;
	}
	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}
	public java.lang.Integer getStockQty() {
		return stockQty;
	}
	public void setStockQty(java.lang.Integer stockQty) {
		this.stockQty = stockQty;
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
	public java.lang.String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(java.lang.String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public java.lang.String getProductAddress() {
		return productAddress;
	}
	public void setProductAddress(java.lang.String productAddress) {
		this.productAddress = productAddress;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	
		
		
}
