package com.zach.gasTrade.dto;

import com.zach.gasTrade.common.PaginatedHelper;

public class DeliveryOrderCompleteDetailDto extends PaginatedHelper implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// 属性
	/**
	 * 订单编号
	 */
	private java.lang.String orderId;
	/**
	 * 商品名称
	 */
	private java.lang.String productName;
	/**
	 * 签收人 
     */ 
	private java.lang.String customerName;
	/**
	 * 签收时间
	 */
	private java.util.Date deliveryCompleteTime;
	/**
	 * 签收人地址
	 */
	private java.lang.String customerAddress;
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
	public java.lang.String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(java.lang.String customerName) {
		this.customerName = customerName;
	}
	public java.util.Date getDeliveryCompleteTime() {
		return deliveryCompleteTime;
	}
	public void setDeliveryCompleteTime(java.util.Date deliveryCompleteTime) {
		this.deliveryCompleteTime = deliveryCompleteTime;
	}
	public java.lang.String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(java.lang.String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	

	
	
	
		
		
}
