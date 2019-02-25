/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.vo;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.zach.gasTrade.common.PaginatedHelper;



public class ProductVo extends PaginatedHelper implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//属性
	    /**
	     * productId 
	     */ 
		private java.lang.String productId;
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
	     * 产品描述 
	     */ 
		private java.lang.String productDesc;
	    /**
	     * imagePath 
	     */ 
		private java.lang.String imagePath;
		/**
	     * image
	     */ 
		private MultipartFile image;
	    /**
	     * 库存数量 
	     */ 
		private java.lang.Integer stockQty;
	    /**
	     * 产品状态:10-正常,20-下架 
	     */ 
		private java.lang.String status;
	    /**
	     * updateTime 
	     */ 
		private java.util.Date updateTime;
	    /**
	     * updateUserId 
	     */ 
		private java.lang.String updateUserId;
	    /**
	     * createTime 
	     */ 
		private java.util.Date createTime;
	    /**
	     * createUserId 
	     */ 
		private java.lang.String createUserId;
	
	//getter setter方法	
		
	public java.lang.String getProductId() {
		return this.productId;
	}
	
	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
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
	
		
	public java.lang.String getSpec() {
		return this.spec;
	}
	
	public void setSpec(java.lang.String value) {
		this.spec = value;
	}
	
		
	public java.lang.String getProductDesc() {
		return this.productDesc;
	}
	
	public void setProductDesc(java.lang.String value) {
		this.productDesc = value;
	}
	
		
	public java.lang.String getImagePath() {
		return this.imagePath;
	}
	
	public void setImagePath(java.lang.String value) {
		this.imagePath = value;
	}
	
		
	public java.lang.Integer getStockQty() {
		return this.stockQty;
	}
	
	public void setStockQty(java.lang.Integer value) {
		this.stockQty = value;
	}
	
		
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
		
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
		
	public java.lang.String getUpdateUserId() {
		return this.updateUserId;
	}
	
	public void setUpdateUserId(java.lang.String value) {
		this.updateUserId = value;
	}
	
		
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
		
	public java.lang.String getCreateUserId() {
		return this.createUserId;
	}
	
	public void setCreateUserId(java.lang.String value) {
		this.createUserId = value;
	}
	
		
}