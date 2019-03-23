package com.zach.gasTrade.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderInfoDetailDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 738580145574477076L;
	/**
	 * 订单ID
	 */
	private String orderId;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 商品金额
	 */
	private BigDecimal amount;
	/**
	 * 支付金额
	 */
	private BigDecimal payAmount;
	/**
	 * 支付时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date payTime;
	/**
	 * 商品库存
	 */
	private Integer stockQty;
	/**
	 * 商品规格
	 */
	private String spec;
	/**
	 * 客户手机
	 */
	private String remark;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 客户地址
	 */
	private String customerAddress;
	/**
	 * 客户手机
	 */
	private String customerPhoneNumber;
	/**
	 * 商家地址
	 */
	private String productAddress;
	/**
	 * 派送员名称
	 */
	private String deliveryName;
	/**
	 * 派送员联系方式
	 */
	private String deliveryPhoneNumber;
	/**
	 * 派送时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deliveryOrderTime;
	/**
	 * 派送完成时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deliveryCompleteTime;
	/**
	 * 商家位置 起点
	 */
	private String startLocation;
	/**
	 * 派送员当前位置（字段无值，取手机当前位置）
	 */
	private String currentLocation;
	/**
	 * 客户位置 终点
	 */
	private String endLocation;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getStockQty() {
		return stockQty;
	}

	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public String getProductAddress() {
		return productAddress;
	}

	public void setProductAddress(String productAddress) {
		this.productAddress = productAddress;
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

	public Date getDeliveryOrderTime() {
		return deliveryOrderTime;
	}

	public void setDeliveryOrderTime(Date deliveryOrderTime) {
		this.deliveryOrderTime = deliveryOrderTime;
	}

	public Date getDeliveryCompleteTime() {
		return deliveryCompleteTime;
	}

	public void setDeliveryCompleteTime(Date deliveryCompleteTime) {
		this.deliveryCompleteTime = deliveryCompleteTime;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

}
