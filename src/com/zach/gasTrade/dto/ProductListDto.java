package com.zach.gasTrade.dto;

import java.math.BigDecimal;

import com.zach.gasTrade.common.PaginatedHelper;

public class ProductListDto extends PaginatedHelper implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// 属性
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
	 * imagePath
	 */
	private java.lang.String imagePath;

	public java.lang.String getProductId() {
		return productId;
	}

	public void setProductId(java.lang.String productId) {
		this.productId = productId;
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

	public java.lang.String getImagePath() {
		return imagePath;
	}

	public void setImagePath(java.lang.String imagePath) {
		this.imagePath = imagePath;
	}

}
