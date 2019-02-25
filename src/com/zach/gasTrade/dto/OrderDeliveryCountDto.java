package com.zach.gasTrade.dto;

import com.zach.gasTrade.common.PaginatedHelper;

public class OrderDeliveryCountDto extends PaginatedHelper implements java.io.Serializable {
private static final long serialVersionUID = 5454155825314635342L;
	
	//属性
	    /**
	     * 派送员姓名 
	     */ 
		private java.lang.String deliveryName;
	    /**
	     * 今日派送完成数量 
	     */ 
		private int dayCompleteTime;
		/**
	     * 今日接单数量 
	     */ 
		private int dayAcceptTime;
	    /**
	     * 本月派送完成数量 
	     */ 
		private int monthCompleteTime;
		/**
	     * 累计派送完成总量 
	     */ 
		private int accumulatedCompleteTime;
		
		//getter setter方法
		
		public java.lang.String getDeliveryName() {
			return deliveryName;
		}
		public void setDeliveryName(java.lang.String deliveryName) {
			this.deliveryName = deliveryName;
		}
		public int getDayCompleteTime() {
			return dayCompleteTime;
		}
		public void setDayCompleteTime(int dayCompleteTime) {
			this.dayCompleteTime = dayCompleteTime;
		}
		public int getDayAcceptTime() {
			return dayAcceptTime;
		}
		public void setDayAcceptTime(int dayAcceptTime) {
			this.dayAcceptTime = dayAcceptTime;
		}
		public int getMonthCompleteTime() {
			return monthCompleteTime;
		}
		public void setMonthCompleteTime(int monthCompleteTime) {
			this.monthCompleteTime = monthCompleteTime;
		}
		public int getAccumulatedCompleteTime() {
			return accumulatedCompleteTime;
		}
		public void setAccumulatedCompleteTime(int accumulatedCompleteTime) {
			this.accumulatedCompleteTime = accumulatedCompleteTime;
		}
		
				
		
	    
}
