package com.zach.gasTrade.dto;

import java.math.BigDecimal;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zach.gasTrade.common.PaginatedHelper;

public class OrderInfoDto extends PaginatedHelper implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// 属性
	/**
	 * orderId
	 */
	private java.lang.String orderId;
	/**
	 * 订单金额
	 */
	private BigDecimal amount;
	/**
	 * 支付状态:10-未支付,20-已支付
	 */
	private java.lang.String payStatus;
	/**
	 * 支付时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private java.util.Date payTime;
	/**
	 * 分配状态:10-未分配，20-已分配
	 */
	private java.lang.String allotStatus;
	/**
	 * 分配时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private java.util.Date allotTime;
	/**
	 * 派送员姓名
	 */
	private java.lang.String allotDeliveryName;
	/**
	 * 派送员电话
	 */
	private java.lang.String allotDeliveryPhoneNumber;
	/**
	 * 接单派送时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private java.util.Date deliveryOrderTime;
	/**
	 * 派送完成时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private java.util.Date deliveryCompleteTime;
	/**
	 * 客户姓名
	 */
	private java.lang.String customerUserName;
	/**
	 * 客户地址
	 */
	private java.lang.String customerAddress;
	/**
	 * 订单状态:10-未支付,20-已支付,30-已分配,40-已接单,50-派送中,60-派送完成
	 */
	private java.lang.String orderStatus;
	/**
	 * 备注
	 */
	private java.lang.String remark;
	/**
	 * 订单编辑原因
	 */
	private java.lang.String editReason;
	/**
	 * updateTime
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private java.util.Date updateTime;

	// getter setter方法

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

	public java.lang.String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(java.lang.String payStatus) {
		this.payStatus = payStatus;
	}

	public java.util.Date getPayTime() {
		return payTime;
	}

	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}

	public java.lang.String getAllotStatus() {
		return allotStatus;
	}

	public void setAllotStatus(java.lang.String allotStatus) {
		this.allotStatus = allotStatus;
	}

	public java.util.Date getAllotTime() {
		return allotTime;
	}

	public void setAllotTime(java.util.Date allotTime) {
		this.allotTime = allotTime;
	}

	public java.lang.String getAllotDeliveryName() {
		return allotDeliveryName;
	}

	public void setAllotDeliveryName(java.lang.String allotDeliveryName) {
		this.allotDeliveryName = allotDeliveryName;
	}

	public java.lang.String getAllotDeliveryPhoneNumber() {
		return allotDeliveryPhoneNumber;
	}

	public void setAllotDeliveryPhoneNumber(java.lang.String allotDeliveryPhoneNumber) {
		this.allotDeliveryPhoneNumber = allotDeliveryPhoneNumber;
	}

	public java.util.Date getDeliveryOrderTime() {
		return deliveryOrderTime;
	}

	public void setDeliveryOrderTime(java.util.Date deliveryOrderTime) {
		this.deliveryOrderTime = deliveryOrderTime;
	}

	public java.util.Date getDeliveryCompleteTime() {
		return deliveryCompleteTime;
	}

	public void setDeliveryCompleteTime(java.util.Date deliveryCompleteTime) {
		this.deliveryCompleteTime = deliveryCompleteTime;
	}

	public java.lang.String getCustomerUserName() {
		return customerUserName;
	}

	public void setCustomerUserName(java.lang.String customerUserName) {
		this.customerUserName = customerUserName;
	}

	public java.lang.String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(java.lang.String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public java.lang.String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(java.lang.String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.String getEditReason() {
		return editReason;
	}

	public void setEditReason(java.lang.String editReason) {
		this.editReason = editReason;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

}
