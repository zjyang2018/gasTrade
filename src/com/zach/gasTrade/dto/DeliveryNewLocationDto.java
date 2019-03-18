package com.zach.gasTrade.dto;

import com.zach.gasTrade.common.PaginatedHelper;

public class DeliveryNewLocationDto extends PaginatedHelper implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1610618394195985338L;
	// 属性
	/**
	 * deliveryId
	 */
	private String deliveryId;
	/**
	 * 派送员名称
	 */
	private String deliveryName;
	/**
	 * 工作状态
	 */
	private String workStatus;
	/**
	 * 当前位置经纬度
	 */
	private String currentLoaction;
	/**
	 * 当前位置地址
	 */
	private String currentLoactionAddress;
	/**
	 * 客户地址(目的地)
	 */
	private String customerAddress;

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getCurrentLoaction() {
		return currentLoaction;
	}

	public void setCurrentLoaction(String currentLoaction) {
		this.currentLoaction = currentLoaction;
	}

	public String getCurrentLoactionAddress() {
		return currentLoactionAddress;
	}

	public void setCurrentLoactionAddress(String currentLoactionAddress) {
		this.currentLoactionAddress = currentLoactionAddress;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

}
