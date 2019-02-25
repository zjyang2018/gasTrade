package com.zach.gasTrade.dto;

import com.zach.gasTrade.common.PaginatedHelper;

public class OrderDeliveryProgressDto extends PaginatedHelper implements java.io.Serializable {
private static final long serialVersionUID = 5454155825314635342L;
	
//属性
/**
 * orderId 
 */ 
private java.lang.String orderId;
/**
 * 派送员名称 
 */ 
private java.lang.String deliveryName;
/**
 * 派送员手机号 
 */ 
private java.lang.String deliveryPhoneNumber;
/**
 * 分配时间 
 */ 
private java.util.Date allotTime;
/**
 * 接单时间 
 */ 
private java.util.Date acceptTime;
/**
 * 派送时间 
 */ 
private java.util.Date deliveryTime;
/**
 * 完成时间 
 */ 
private java.util.Date completeTime;
/**
 * 起始经纬度 
 */ 
private java.lang.String startLocation;
/**
 * 终点经纬度 
 */ 
private java.lang.String endLocation;
/**
 * 移动中的经纬度 
 */ 
private java.lang.String moveLocation;

//getter setter方法

public java.lang.String getOrderId() {
	return orderId;
}
public void setOrderId(java.lang.String orderId) {
	this.orderId = orderId;
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
public java.util.Date getAllotTime() {
	return allotTime;
}
public void setAllotTime(java.util.Date allotTime) {
	this.allotTime = allotTime;
}
public java.util.Date getAcceptTime() {
	return acceptTime;
}
public void setAcceptTime(java.util.Date acceptTime) {
	this.acceptTime = acceptTime;
}
public java.util.Date getDeliveryTime() {
	return deliveryTime;
}
public void setDeliveryTime(java.util.Date deliveryTime) {
	this.deliveryTime = deliveryTime;
}
public java.util.Date getCompleteTime() {
	return completeTime;
}
public void setCompleteTime(java.util.Date completeTime) {
	this.completeTime = completeTime;
}
public java.lang.String getStartLocation() {
	return startLocation;
}
public void setStartLocation(java.lang.String startLocation) {
	this.startLocation = startLocation;
}
public java.lang.String getEndLocation() {
	return endLocation;
}
public void setEndLocation(java.lang.String endLocation) {
	this.endLocation = endLocation;
}
public java.lang.String getMoveLocation() {
	return moveLocation;
}
public void setMoveLocation(java.lang.String moveLocation) {
	this.moveLocation = moveLocation;
}

				
	    
}
