/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.vo;

import java.math.BigDecimal;

import com.zach.gasTrade.common.PaginatedHelper;

public class OrderInfoVo extends PaginatedHelper implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// 属性
	/**
	 * orderId
	 */
	private java.lang.String orderId;
	/**
	 * productId
	 */
	private java.lang.String productId;
	/**
	 * productName
	 */
	private java.lang.String productName;
	/**
	 * 订单金额
	 */
	private BigDecimal amount;
	/**
	 * 支付状态:10-未支付,20-已支付
	 */
	private java.lang.String payStatus;
	/**
	 * 支付流水号
	 */
	private java.lang.String payNo;

	/**
	 * 支付金额
	 */
	private BigDecimal realPayAmount;
	/**
	 * 支付时间
	 */
	private java.util.Date payTime;
	/**
	 * 分配状态:10-未分配，20-已分配
	 */
	private java.lang.String allotStatus;
	/**
	 * 分配时间
	 */
	private java.util.Date allotTime;
	/**
	 * 派送员ID
	 */
	private java.lang.String allotDeliveryId;
	/**
	 * 接单派送时间
	 */
	private java.util.Date deliveryOrderTime;
	/**
	 * 派送完成时间
	 */
	private java.util.Date deliveryCompleteTime;
	/**
	 * 客户ID
	 */
	private java.lang.String customerUserId;
	/**
	 * 客户地址
	 */
	private java.lang.String customerAddress;
	/**
	 * 客户地址经度
	 */
	private java.lang.String longitude;
	/**
	 * 客户地址纬度
	 */
	private java.lang.String latitude;
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
	 * createTime
	 */
	private java.util.Date createTime;
	/**
	 * updateTime
	 */
	private java.util.Date updateTime;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 客户手机号
	 */
	private String customerPhoneNumber;

	/**
	 * 派送员名称
	 */
	private String deliveryName;

	/**
	 * 派送员手机号
	 */
	private String deliveryPhoneNumber;

	/**
	 * 查询条件
	 */
	private String selectCustomerParam;

	private String selectDeliveryParam;

	private String startCreateTime;

	private String endCreateTime;

	// getter setter方法

	public java.lang.String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(java.lang.String value) {
		this.orderId = value;
	}

	public java.lang.String getProductId() {
		return this.productId;
	}

	public void setProductId(java.lang.String value) {
		this.productId = value;
	}

	public java.lang.String getProductName() {
		return this.productName;
	}

	public void setProductName(java.lang.String value) {
		this.productName = value;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal value) {
		this.amount = value;
	}

	public java.lang.String getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(java.lang.String value) {
		this.payStatus = value;
	}

	public java.lang.String getPayNo() {
		return this.payNo;
	}

	public void setPayNo(java.lang.String value) {
		this.payNo = value;
	}

	public java.util.Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(java.util.Date value) {
		this.payTime = value;
	}

	public java.lang.String getAllotStatus() {
		return this.allotStatus;
	}

	public void setAllotStatus(java.lang.String value) {
		this.allotStatus = value;
	}

	public java.util.Date getAllotTime() {
		return this.allotTime;
	}

	public void setAllotTime(java.util.Date value) {
		this.allotTime = value;
	}

	public java.lang.String getAllotDeliveryId() {
		return this.allotDeliveryId;
	}

	public void setAllotDeliveryId(java.lang.String value) {
		this.allotDeliveryId = value;
	}

	public java.util.Date getDeliveryOrderTime() {
		return this.deliveryOrderTime;
	}

	public void setDeliveryOrderTime(java.util.Date value) {
		this.deliveryOrderTime = value;
	}

	public java.util.Date getDeliveryCompleteTime() {
		return this.deliveryCompleteTime;
	}

	public void setDeliveryCompleteTime(java.util.Date value) {
		this.deliveryCompleteTime = value;
	}

	public java.lang.String getCustomerUserId() {
		return this.customerUserId;
	}

	public void setCustomerUserId(java.lang.String value) {
		this.customerUserId = value;
	}

	public java.lang.String getCustomerAddress() {
		return this.customerAddress;
	}

	public void setCustomerAddress(java.lang.String value) {
		this.customerAddress = value;
	}

	public java.lang.String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(java.lang.String value) {
		this.longitude = value;
	}

	public java.lang.String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(java.lang.String value) {
		this.latitude = value;
	}

	public java.lang.String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(java.lang.String value) {
		this.orderStatus = value;
	}

	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setRemark(java.lang.String value) {
		this.remark = value;
	}

	public java.lang.String getEditReason() {
		return this.editReason;
	}

	public void setEditReason(java.lang.String value) {
		this.editReason = value;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}

	public BigDecimal getRealPayAmount() {
		return realPayAmount;
	}

	public void setRealPayAmount(BigDecimal realPayAmount) {
		this.realPayAmount = realPayAmount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getDeliveryPhoneNumber() {
		return deliveryPhoneNumber;
	}

	public void setDeliveryPhoneNumber(String deliveryPhoneNumber) {
		this.deliveryPhoneNumber = deliveryPhoneNumber;
	}

	public String getSelectCustomerParam() {
		return selectCustomerParam;
	}

	public void setSelectCustomerParam(String selectCustomerParam) {
		this.selectCustomerParam = selectCustomerParam;
	}

	public String getSelectDeliveryParam() {
		return selectDeliveryParam;
	}

	public void setSelectDeliveryParam(String selectDeliveryParam) {
		this.selectDeliveryParam = selectDeliveryParam;
	}

	public String getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(String startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public String getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

}