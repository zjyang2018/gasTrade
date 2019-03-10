package com.zach.gasTrade.dto;

import java.math.BigDecimal;

import com.zach.gasTrade.common.PaginatedHelper;

public class OrderDetailDto extends PaginatedHelper implements java.io.Serializable {
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
	 * 产品规格
	 */
	private java.lang.String spec;
	/**
	 * 支付金额
	 */
	private BigDecimal payAmount;
	/**
	 * 支付时间
	 */
	private java.util.Date payTime;
	/**
	 * 备注
	 */
	private java.lang.String remark;
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public java.lang.String getSpec() {
		return spec;
	}
	public void setSpec(java.lang.String spec) {
		this.spec = spec;
	}
	public java.util.Date getPayTime() {
		return payTime;
	}
	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
		
}
